package net.ebaolife.husqvarna.framework.utils;

import com.alibaba.fastjson.JSON;
import net.ebaolife.husqvarna.framework.bean.TreeNode;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

@SuppressWarnings("unchecked")
public class TreeBuilder {

	private static String idkey = "id";
	private static String menuidkey = "menuid";
	private static String textkey = "text";
	private static String parentidkey = "parentId";
	private static String childrenkey = "children";

	public static <T> List<T> buildListToTree(List<T> dirs) {
		if (dirs == null || dirs.size() == 0)
			return dirs;
		if (!(dirs.get(0) instanceof TreeNode || dirs.get(0) instanceof Map))
			return dirs;
		List<T> roots = findRoots(dirs);
		List<T> notRoots = (List<T>) CollectionUtils.subtract(dirs, roots);
		for (T root : roots) {
			if (root instanceof TreeNode) {
				((TreeNode) root).setChildren((List<TreeNode>) findChildren(root, notRoots));
			} else if (root instanceof Map) {
				((Map<String, Object>) root).put(childrenkey, findChildren(root, notRoots));
			}
		}
		return roots;
	}

	private static <T> List<T> findRoots(List<T> allTreeNodes) {
		List<T> results = new ArrayList<T>();
		for (T t : allTreeNodes) {
			boolean isRoot = true;
			if (t instanceof TreeNode) {
				TreeNode node = (TreeNode) t;
				String parentId = node.getParentId();
				node.setParentId(CommonUtils.isEmpty(parentId) ? "00" : parentId);
				for (T t1 : allTreeNodes) {
					TreeNode comparedOne = (TreeNode) t1;
					String id = CommonUtils.isEmpty(comparedOne.getId()) ? comparedOne.getMenuid()
							: comparedOne.getId();
					if (node.getParentId().equals(id)) {
						isRoot = false;
						break;
					}
				}
				if (isRoot) {
					node.setLevel(0);
					results.add((T) node);
				}
			} else if (t instanceof Map) {
				Map<String, Object> node = (Map<String, Object>) t;
				String parentId = (String) node.get(parentidkey);
				node.put(parentidkey, CommonUtils.isEmpty(parentId) ? "00" : parentId);
				for (T t1 : allTreeNodes) {
					Map<String, Object> comparedOne = (Map<String, Object>) t1;
					Object id = CommonUtils.isEmpty(comparedOne.get(idkey)) ? comparedOne.get(menuidkey)
							: comparedOne.get(idkey);
					if (node.get(parentidkey).equals(id)) {
						isRoot = false;
						break;
					}
				}
				if (isRoot) {
					node.put("level", 0);
					results.add((T) node);
				}
			}
		}
		sort(results);
		return results;
	}

	private static <T> List<T> findChildren(T root, List<T> allTreeNodes) {
		List<T> children = new ArrayList<T>();
		for (T t : allTreeNodes) {
			if (t instanceof TreeNode) {
				TreeNode comparedOne = (TreeNode) t;
				TreeNode root1 = (TreeNode) root;
				String id = CommonUtils.isEmpty(root1.getId()) ? root1.getMenuid() : root1.getId();
				if (comparedOne.getParentId().equals(id)) {
					comparedOne.setLevel(root1.getLevel() + 1);
					children.add((T) comparedOne);
				}
			} else if (t instanceof Map) {
				Map<String, Object> comparedOne = (Map<String, Object>) t;
				Map<String, Object> root1 = (Map<String, Object>) root;
				Object id = CommonUtils.isEmpty(root1.get(idkey)) ? root1.get(menuidkey) : root1.get(idkey);
				if (comparedOne.get(parentidkey).equals(id)) {
					comparedOne.put("level", (int) root1.get("level") + 1);
					children.add((T) comparedOne);
				}
			}
		}
		List<T> notChildren = (List<T>) CollectionUtils.subtract(allTreeNodes, children);
		for (T t : children) {
			if (t instanceof TreeNode) {
				List<TreeNode> tmpChildren = (List<TreeNode>) findChildren(t, notChildren);
				TreeNode child = (TreeNode) t;
				if (tmpChildren == null || tmpChildren.size() < 1) {
					child.setLeaf(true);
				} else {
					child.setLeaf(false);
				}
				child.setChildren((List<TreeNode>) tmpChildren);
			} else if (t instanceof Map) {
				List<Map<String, Object>> tmpChildren = (List<Map<String, Object>>) findChildren(t, notChildren);
				Map<String, Object> child = (Map<String, Object>) t;
				if (tmpChildren == null || tmpChildren.size() < 1) {
					child.put("leaf", true);
				} else {
					child.put("leaf", false);
				}
				child.put(childrenkey, tmpChildren);
			}
		}
		sort(children);
		return children;
	}

	public static <T> void addGroupNode(List<T> list, String type, String typekey, String text, String icon) {
		List<T> btnslist = new ArrayList<T>();
		Map<String, T> map = new HashMap<String, T>();
		for (int i = 0; i < list.size(); i++) {
			T t = list.get(i);
			if (t instanceof TreeNode) {
				TreeNode tn = (TreeNode) t;
				if (tn.getParam3().equals(type)) {
					String menuid = tn.getParentId();
					TreeNode node = (TreeNode) map.get(menuid);
					if (node == null) {
						node = new TreeNode();
						node.setId(tn.getId() + "_" + type);
						node.setParentId(tn.getParentId());
						node.setText(text);
						if (icon.startsWith("x-fa")) {
							node.setIconCls(icon);
						} else {
							node.setIcon(icon);
						}
						node.setDisabled(true);
						map.put(menuid, (T) node);
						btnslist.add((T) node);
					}
					tn.setParentId(node.getId());
				}
			} else if (t instanceof Map) {
				Map<String, Object> tn = (Map<String, Object>) t;
				if (tn.get(typekey).equals(type)) {
					String menuid = (String) tn.get(parentidkey);
					Map<String, Object> node = (Map<String, Object>) map.get(menuid);
					if (node == null) {
						node = new LinkedHashMap<String, Object>();
						node.put(idkey, tn.get(idkey) + "_" + type);
						node.put(parentidkey, tn.get(parentidkey));
						node.put(textkey, text);
						if (icon.startsWith("x-fa")) {
							node.put("iconCls", icon);
						} else {
							node.put("icon", icon);
						}
						node.put("disabled", true);
						map.put(menuid, (T) node);
						btnslist.add((T) node);
					}
					tn.put(parentidkey, node.get(idkey));
				}
			}
		}
		list.addAll(btnslist);
	}

	private static <T> void sort(List<T> list) {
		Collections.sort(list, new Comparator<T>() {
			public int compare(T a, T b) {
				Integer one = null;
				Integer two = null;
				if (a instanceof TreeNode) {
					one = ((TreeNode) a).getOrderno();
					two = ((TreeNode) b).getOrderno();
				} else if (a instanceof Map) {
					one = Integer.valueOf(((Map<String, Object>) a).get("orderno").toString());
					two = Integer.valueOf(((Map<String, Object>) b).get("orderno").toString());
				}
				if (one == null || two == null)
					return 0;
				return one - two;
			}
		});
	}

	@SuppressWarnings("serial")
	public static void main(String[] args) {
		List<TreeNode> allTreeNodes = new ArrayList<TreeNode>();
		allTreeNodes.add(new TreeNode("1", "0", "节点1"));
		allTreeNodes.add(new TreeNode("2", "0", "节点2"));
		allTreeNodes.add(new TreeNode("3", "0", "节点3"));
		allTreeNodes.add(new TreeNode("4", "1", "节点4"));
		allTreeNodes.add(new TreeNode("5", "1", "节点5"));
		allTreeNodes.add(new TreeNode("6", "1", "节点6"));
		allTreeNodes.add(new TreeNode("7", "4", "节点7"));
		allTreeNodes.add(new TreeNode("8", "4", "节点8"));
		allTreeNodes.add(new TreeNode("9", "5", "节点9"));
		allTreeNodes.add(new TreeNode("10", "100", "节点10"));
		allTreeNodes.get(0).setIcon("plugin.gif");
		List<TreeNode> roots = buildListToTree(allTreeNodes);
		System.out.println(JSON.toJSONString(roots));

		List<Map<String, Object>> allTreeNodes1 = new ArrayList<Map<String, Object>>();
		allTreeNodes1.add(new LinkedHashMap<String, Object>() {
			{
				put(idkey, "1");
				put(parentidkey, "0");
				put(textkey, "text1");
			}
		});
		allTreeNodes1.add(new LinkedHashMap<String, Object>() {
			{
				put(idkey, "2");
				put(parentidkey, "0");
				put(textkey, "text2");
			}
		});
		allTreeNodes1.add(new LinkedHashMap<String, Object>() {
			{
				put(idkey, "3");
				put(parentidkey, "1");
				put(textkey, "text3");
			}
		});
		List<Map<String, Object>> roots1 = buildListToTree(allTreeNodes1);
		System.out.println(JSON.toJSONString(roots1));
	}

	public static void setIdkey(String idkey) {
		TreeBuilder.idkey = idkey;
	}

	public static void setMenuidkey(String menuidkey) {
		TreeBuilder.menuidkey = menuidkey;
	}

	public static void setTextkey(String textkey) {
		TreeBuilder.textkey = textkey;
	}

	public static void setParentidkey(String parentidkey) {
		TreeBuilder.parentidkey = parentidkey;
	}

	public static void setChildrenkey(String childrenkey) {
		TreeBuilder.childrenkey = childrenkey;
	}
}
