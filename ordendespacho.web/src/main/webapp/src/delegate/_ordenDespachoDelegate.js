App.Delegate.OrdenDespachoDelegate = {
        search: function(user, callback, callbackError) {
            console.log('OrdenDespacho Search: ');
            $.ajax({
                url: '/ordendespacho.service.subsystem.web/webresources/OrdenDespacho/search',
                type: 'POST',
                data: JSON.stringify(user),
                contentType: 'application/json'
            }).done(_.bind(function(data) {
                callback(data);
            }, this)).error(_.bind(function(data) {
                callbackError(data);
            }, this));
        }
    };