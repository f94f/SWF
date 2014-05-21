define(['controller/_ordenDespachoController'], function() {
    App.Controller.OrdenDespachoController = App.Controller._OrdenDespachoController.extend({
       
        postInit: function(options){
            var self = this;
            Backbone.on('ordenDespacho-model-error', function(params) {
                var error = params.error;
                Backbone.trigger(self.componentId + '-' + 'error',
                         {event: 'ordenDespachoq-model', view: self, error:{ responseText: error}});
            });
        }
       
    });
    return App.Controller.OrdenDespachoController;
}); 