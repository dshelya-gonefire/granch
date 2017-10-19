'use strict';

angular.module('grunch').controller('GitController',
    ['GitService', '$scope',  function(GitService, $scope) {

        var self = this;
        self.getRecentAcitivy = getRecentAcitivy;
        
        function getRecentAcitivy(){
            return GitService.getRecentAcitivy();
        }
    }
]);