<div class="generic-container">
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="h2">Recent git branches</span></div>
		<div class="panel-body">
			<div class="table-responsive">
		        <table class="table table-hover">
		            <thead>
		            <tr>
		                <th width="300">Branch</th>
		                <th width="200">Last commiter</th>
		                <th width="200">Commit date</th>
		                <th width="200">Commit hash</th>
		                <th>Commit message</th>		       
		            </tr>
		            </thead>
		            <tbody>
		            <tr ng-repeat="u in ctrl.getRecentAcitivy()">
		                <td>{{u.branchName}}</td>
		                <td>{{u.author}}</td>
		                <td>{{u.commitDate}}</td>
		                <td>{{u.commitHash}}</td>
		                <td>{{u.commitMessage}}</td>
		            </tr>
		            </tbody>
		        </table>		
			</div>
		</div>
    </div>
</div>