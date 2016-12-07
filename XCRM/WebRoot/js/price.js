$(function() {
	$('.create.btn.btn-primary').click(function() {
		$.ajax({
			type : "get",
			url : "/price/preadd?pid="+$('#table').data()['bootstrap.table'].data[index].product,
			success : function(data, status) {
				if (status == "success") {
					$('.bootstrap-table').html(data);
				}
			}
		});
	});

	xcpage.tableInitHandler = function(data) {
		$('.update').click(function() {
			var index = $(this).parents('tr').attr('data-index');
			$.ajax({
				type : "get",
				url : "/price/preadd?pid="+$('#table').data()['bootstrap.table'].data[index].product,
				success : function(data, status) {
					if (status == "success") {
						$('.bootstrap-table').html(data);
					}
				}
			});
			
			var urlhere = "/price/loadingAddData?id="+$('#table').data()['bootstrap.table'].data[index].id;
			setTimeout(function() {
				$.ajax({
					type : "get",
					url : urlhere,
					success : function(indata, status) {
						if (status == "success") {
							loadData('test', indata);
						}
					}
				})
			}, 1000);
		});
	};
	
	$('.modal-footer .btn.btn-default').click(function() {
		window.location.href = '/price/';
	});

	$('.modal-footer .btn.btn-primary.submit').click(function() {
		$('#modal-form').submit();
	});
	
	//get product list
	$.get('/product/list/', function(data) {
		productList = data.rows;
		for (d in productList) {
			$('#productselect').append(
					"<option value='" + productList[d].id + "'>"
							+ productList[d].name + "</option>");
		}
	});
	
	
	function loadData(title, row) {
		form = $('#modal-form');
		row = row || {
			id : '',
		}; // default row value
//		if(form){
//			form.reset();//reset form
//			form.find("[multiple='multiple']").multiselect('deselectAll',false);
//			form.find("[multiple='multiple']").multiselect('updateButtonText');
//		}
		form.data('id', row.id);
		form.find('.modal-title').text(title);
		for ( var name in row) {
			//set value to dialog for radio input
			if( form.find('input[name="' + name + '"]').attr('type') == "radio"){
				form.find('input[name="' + name + '"]').each(function() {
					if( $(this).val() == row[name]){
						$(this).attr("checked", true);
					}else{
						$(this).removeAttr("checked");
					}
				});
			}else{
				//set value to dialog for input
				form.find('input[name="' + name + '"]').val(row[name]);
				//set value to dialog for select
				form.find('select[name="' + name + '"]').val(row[name]);
				// set value for dialog for multiple select
				if(form.find('select[name="' + name + '"]').attr('multiple') == "multiple"){
					if(row[name]){
						form.find('select[name="' + name + '"]').multiselect('select', row[name].split(","));
					}else{
						form.find('select[name="' + name + '"]').multiselect('deselectAll',false);
						form.find('select[name="' + name + '"]').multiselect('updateButtonText');
					}
				}
			}
		}
	};

});
