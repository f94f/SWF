define(['controller/_ordenReaprovicionamientoController'], function() {
    App.Controller.OrdenReaprovicionamientoController = App.Controller._OrdenReaprovicionamientoController.extend({
        postInit: function(options){
            var self = this;
            Backbone.on('ordenReaprovicionamiento-model-error', function(params) {
                var error = params.error;
                Backbone.trigger(self.componentId + '-' + 'error',
                         {event: 'ordenReaprovicionamientoq-model', view: self, error:{ responseText: error}});
            });
        }
       
    });
    return App.Controller.OrdenReaprovicionamientoController;
}); 