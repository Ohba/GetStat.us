var App = Ember.Application.create({});
App.Store = DS.Store.extend({
    revision: 12,
    url: 'http://localhost:8080'
});

App.Cloud = DS.Model.extend({
    name: DS.attr('string'),
    status: DS.attr('string'),
    url: DS.attr('string'),
    timeStamp: DS.attr('date'),
    responseType: DS.attr('string')
});

App.IndexRoute = Ember.Route.extend({
    setupController: function(controller) {
        //Seriously I need to use jQuery??? WTF Ember!
        $.getJSON("/clouds", function(clouds){
            controller.set('content', clouds);
        });
    }
});