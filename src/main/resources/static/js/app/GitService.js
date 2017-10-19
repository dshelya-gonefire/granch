'use strict';

angular.module('grunch').factory('GitService',
    ['$localStorage', '$http', '$q', 'urls',
        function ($localStorage, $http, $q, urls) {

            var factory = {
                listRecentAcitivy: listRecentAcitivy,
                getRecentAcitivy: getRecentAcitivy              
            };

            return factory;

            function listRecentAcitivy() {
                console.log('Fetching all git branches');
                var deferred = $q.defer();
                $http.get(urls.GIT_SERVICE_API)
                    .then(
                        function (response) {
                            console.log('Fetched successfully all branches');
                            $localStorage.activity = response.data;
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while loading git branches');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function getRecentAcitivy(){
                return $localStorage.activity;
            }
        }
    ]);