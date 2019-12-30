Ext.define('app.view.platform.frame.SystemFrame', {
    extend: 'Ext.panel.Panel',
    alternateClassName: 'systemFrame',
    xtype: 'systemFrame',
    requires: [
        'app.view.platform.frame.SystemFrameController'
    ],
    controller:'systemframe',
    layout: 'fit',
    referenceHolder: true
});