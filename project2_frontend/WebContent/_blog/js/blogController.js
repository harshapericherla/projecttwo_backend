app.controller('blogController',function($scope,$location,blogService){
	
	$scope.saveBlogPost = function(){
		blogService.saveBlogPost($scope.blog)
		           .then(function(resp){
		        	   $scope.sucMess = "Your blog is posted successfully and waiting for approval";
		        	   $location.path('/getallblogs');
		           },function(resp){
		        	   
		        	   console.log(resp.status);
		        	   $location.path('/login');
		           });
	}
});