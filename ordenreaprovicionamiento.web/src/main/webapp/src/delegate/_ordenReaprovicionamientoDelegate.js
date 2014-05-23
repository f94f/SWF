App.Delegate.OrdenReaprovicionamientoDelegate = {

    search: function(user, callback, callbackError) {
            console.log('OrdenReaprovicionamiento Search: ');
            $.ajax({
                url: '/ordenreaprovicionamiento.service.subsystem.web/webresources/OrdenReaprovicionamiento/search',
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