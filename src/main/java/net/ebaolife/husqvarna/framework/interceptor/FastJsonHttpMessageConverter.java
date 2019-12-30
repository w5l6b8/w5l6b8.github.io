package net.ebaolife.husqvarna.framework.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import net.ebaolife.husqvarna.framework.critical.Local;
import net.ebaolife.husqvarna.framework.utils.CommonUtils;
import net.ebaolife.husqvarna.framework.utils.PropertyPreFilters;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FastJsonHttpMessageConverter extends com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter {

	private FastJsonConfig fastJsonConfig = new FastJsonConfig();
	private String datetimeFormat = "yyyy-MM-dd HH:mm:ss";
	private String dateFormat = "yyyy-MM-dd";

	public FastJsonHttpMessageConverter() {
		SerializeConfig config = fastJsonConfig.getSerializeConfig();
		config.put(java.util.Date.class, new SimpleDateFormatSerializer(datetimeFormat));
		config.put(java.sql.Date.class, new SimpleDateFormatSerializer(dateFormat));
		config.put(java.sql.Timestamp.class, new SimpleDateFormatSerializer(datetimeFormat));
	}

	@Override
	protected void writeInternal(Object obj, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		HttpHeaders headers = outputMessage.getHeaders();
		ByteArrayOutputStream outnew = new ByteArrayOutputStream();
		List<SerializeFilter> serializerFeatures = new ArrayList<SerializeFilter>();
		Collections.addAll(serializerFeatures, fastJsonConfig.getSerializeFilters());
		OutputStream out = outputMessage.getBody();
		boolean writeAsToString = false;
		if (obj != null) {
			String className = obj.getClass().getName();
			if ("com.fasterxml.jackson.databind.node.ObjectNode".equals(className)) {
				writeAsToString = true;
			}
		}
		if (writeAsToString) {
			String text = obj.toString();
			outnew.write(text.getBytes());
		} else {
			PropertyPreFilters features = Local.getJsonToHttpFilters();
			if (features != null) {
				serializerFeatures.addAll(features.getFilters());
				Local.clearJsonToHttpFilters();
			}
			JSON.writeJSONString(outnew, fastJsonConfig.getCharset(), obj, fastJsonConfig.getSerializeConfig(),
					serializerFeatures.toArray(new SimplePropertyPreFilter[] {}), fastJsonConfig.getDateFormat(),
					JSON.DEFAULT_GENERATE_FEATURE, fastJsonConfig.getSerializerFeatures());
		}
		String callback = isJsonp();
		if (!CommonUtils.isEmpty(callback)) {
			String text = callback + "(" + outnew.toString() + ")";
			outnew.reset();
			outnew.write(text.getBytes());
		}
		headers.setContentLength(outnew.size());
		outnew.writeTo(out);
		out.close();
	}

	private String isJsonp() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String callback = request.getParameter("callback");
		return callback;
	}
}
