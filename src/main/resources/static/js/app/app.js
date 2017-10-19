var app = angular.module('grunch',['ui.router','ngStorage']);

app.constant('urls', {
    BASE: 'http://localhost/',
    GIT_SERVICE_API : 'http://localhost/api/git/'
});

app.config(['$stateProvider', '$urlRouterProvider',
    function($stateProvider, $urlRouterProvider) {

        $stateProvider
            .state('home', {
                url: '/',
                templateUrl: 'partials/list',
                controller:'GitController',
                controllerAs:'ctrl',
                resolve: {
                    users: function ($q, GitService) {
                        console.log('Load all branch activity');
                        var deferred = $q.defer();
                        GitService.listRecentAcitivy().then(deferred.resolve, deferred.resolve);
                        return deferred.promise;
                    }
                }
            });
        $urlRouterProvider.otherwise('/');
    }]);

