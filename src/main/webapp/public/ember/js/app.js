var App = Ember.Application.create({});

App.cloud = DS.Model.extend({
    name: DS.attr('string'),
    status: DS.attr('string'),
    url: DS.attr('string'),
    timeStamp: DS.attr('date'),
    responseType: DS.attr('string')
});


App.IndexRoute = Ember.Route.extend({
    setupController: function(controller) {
        var clouds = App.cloud.all();
        console.log(clouds);
        controller.set('content', clouds);
    }
});

$(function() {
    App.initialize();
});