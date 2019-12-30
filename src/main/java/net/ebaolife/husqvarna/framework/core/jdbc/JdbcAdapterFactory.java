package net.ebaolife.husqvarna.framework.core.jdbc;

import net.ebaolife.husqvarna.framework.core.jdbc.support.mysql.MySQL5SqlFunction;

public class JdbcAdapterFactory {

	public static MySQL5SqlFunction getJdbcAdapter(String jdbcType) {

		return MySQL5SqlFunction.getInstance();

	}

	public static MySQL5SqlFunction getJdbcAdapter(JdbcType jdbcType) {

		return MySQL5SqlFunction.getInstance();

	}

}
