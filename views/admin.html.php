<?php 
$user = unserialize(h($user));
$urls = unserialize(h($urls)); ?>
<div class="main">
	<div class="container">
	  <div class="row">
	    <div class="span4">
			<table class="table table-bordered">
				<thead>
					<tr>
						<th>Urls</th>
						<th>Shortened</th>
					</tr>
				</thead>
				<tbody>
					<?php foreach ($urls as  $url) {?>
						<tr>
							<td data-id="<?php echo $url['id'] ?>"><?php echo $url['destination'] ?> </td>
							<td><a href="http://getstat.us/g/<?php echo $url['short'] ?>">getstat.us/g/<?php echo $url['short'] ?></a></td>
						</tr>
					<?php } ?>
				</tbody>
			</table>
	    </div>
	    <div class="span8">
	      <div id="line"></div>
	      <div id="donut"></div>
	    </div>
	  </div>
	</div>
</div>
<script type="text/javascript">
  	Morris.Line({
	  element: 'line',
	  data: [
	    {y: '2012', a: 100},
	    {y: '2011', a: 75},
	    {y: '2010', a: 50},
	    {y: '2009', a: 75},
	    {y: '2008', a: 50},
	    {y: '2007', a: 75},
	    {y: '2006', a: 100}
	  ],
	  xkey: 'y',
	  ykeys: ['a'],
	  labels: ['Series A']
	});
	Morris.Donut({
		  element: 'donut',
		  data: [
		    {label: "twitter.com", value: 12},
		    {label: "facebook.com", value: 30},
		    {label: "plus.google.com", value: 20},
		    {label: "unspecified", value: 10}
		  ]
		});
</script>