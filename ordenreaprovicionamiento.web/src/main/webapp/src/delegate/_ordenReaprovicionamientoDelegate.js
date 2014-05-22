App.Delegate.FacturaDelegate = {
        search: function(user, callback, callbackError) {
            console.log('Factura Search: ');
            $.ajax({
                url: '/factura.service.subsystem.web/webresources/Factura/search',
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