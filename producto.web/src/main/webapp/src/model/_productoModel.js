define([], function() {
    App.Model._ProductoModel = Backbone.Model.extend({
        defaults: {
 
		 'name' : '' ,  
		 'tipo' : '' ,  
		 'esPerecedero' :  false  ,  
		 'precioPromedio' : '' ,  
		 'tiempoPromedioEspera' : '' ,  
		 'cantidadPromedioAPedir' : '' ,  
		 'minimoNivelInventario' : ''        },
        initialize: function() {
        },
        getDisplay: function(name) {
          
         if(name=='esPerecedero'){
                 return this.get('esPerecedero')==true?"Sí":"No";
             } 
          
         return this.get(name);
        }
    });

    App.Model._ProductoList = Backbone.Collection.extend({
        model: App.Model._ProductoModel,
        initialize: function() {
        }

    });
    return App.Model._ProductoModel;
});