define([], function() {
    App.Model._ItemModel = Backbone.Model.extend({
        defaults: {
 
		 'name' : '' ,  
		 'fechaCaducidad' : '' ,  
		 'reservado' :  false  ,  
		 'motivoIngreso' : 'No ha ingresado aún a la bodega' ,  
		 'motivoSalida' : 'No ha salido aún de la bodega' ,
		 'enBodega' :  false,
                 'salioDeBodega' : false },
        initialize: function() {
        },
        getDisplay: function(name) {
             if(name=='fechaCaducidad'){
                   var dateConverter = App.Utils.Converter.date;
                   return dateConverter.unserialize(this.get('fechaCaducidad'), this);
             }
             
             if(name=='reservado'){
                 return this.get('reservado')==true?"Sí":"No";
             }
             
             if(name=='enBodega'){
                 return this.get('enBodega')==true?"Sí":"No";
             }
             
         return this.get(name);
        }
    });

    App.Model._ItemList = Backbone.Collection.extend({
        model: App.Model._ItemModel,
        initialize: function() {
        }

    });
    return App.Model._ItemModel;
});