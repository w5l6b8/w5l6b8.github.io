Ext.define('expand.overrides.layout.container.Table', {
	override : 'Ext.layout.container.Table',

	getRenderTree : function(){
		var me = this, items = me.getLayoutItems(), rows = [], result = Ext.apply({
			tag : 'table',
			id : me.owner.id + '-table',
			"data-ref" : 'table',
			role : 'presentation',
			cls : me.tableCls,
			cellspacing : 0,
			cellpadding : 0,
			cn : {
				tag : 'tbody',
				id : me.owner.id + '-tbody',
				"data-ref" : 'tbody',
				role : 'presentation',
				cn : rows
			}
		}, me.tableAttrs), tdAttrs = me.tdAttrs, i, len = items.length, item, curCell, tr, rowIdx, cellIdx, cell, cells;

		// Calculate the correct cell structure for the current items
		cells = me.calculateCells(items);

		var widths = null;
		if (Ext.isObject(tdAttrs)) {
			if (Ext.isString(tdAttrs.widths)) {
				widths = tdAttrs.widths.split(",");
			} else if (Ext.isArray(tdAttrs.widths)) {
				widths = tdAttrs.widths;
			}
			delete tdAttrs.widths;
		}

		for (i = 0; i < len; i++) {
			item = items[i];

			curCell = cells[i];
			rowIdx = curCell.rowIdx;
			cellIdx = curCell.cellIdx;

			// If no row present, create and insert one
			tr = rows[rowIdx];
			if (!tr) {
				tr = rows[rowIdx] = {
					tag : 'tr',
					role : 'presentation',
					cn : []
				};
				if (me.trAttrs) {
					Ext.apply(tr, me.trAttrs);
				}
			}
			// If no cell present, create and insert one
			cell = tr.cn[cellIdx] = {
				tag : 'td',
				role : 'presentation'
			};
			if (tdAttrs) {
				cell.style = {};
				if (Ext.isArray(tdAttrs)) {
					if (i < tdAttrs.length) {
						Ext.apply(cell, tdAttrs[i]);
					}
				} else if (Ext.isObject(tdAttrs)) {
					if (widths && i < widths.length) {
						cell.style.width = widths[i];
					}
					Ext.apply(cell, tdAttrs);
				}
			}
			Ext.apply(cell, {
				colSpan : item.colspan || 1,
				rowSpan : item.rowspan || 1,
				cls : me.cellCls + ' ' + (item.cellCls || '')
			});

			me.configureItem(item);
			// The DomHelper config of the item is the cell's sole child
			cell.cn = item.getRenderTree();
		}
		return result;
	}
});
