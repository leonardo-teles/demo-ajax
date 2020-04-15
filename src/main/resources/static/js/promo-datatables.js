$(document).ready(function() {
	
	moment.locale('pt-br');
	
	var table = $("#table-server").DataTable({
		processing: true,
		serverSide: true,
		responsive: true,
		lengthMenu: [10, 15, 20, 25],
		ajax: {
			url: "/promocao/datatables/server",
			data: "data"
		},
		columns: [
			{data: 'id'},
			{data: 'titulo'},
			{data: 'site'},
			{data: 'linkPromocao'},
			{data: 'descricao'},
			{data: 'linkImagem'},
			{data: 'preco', render: $.fn.dataTable.render.number('.', ',', 2, 'R$ ')},
			{data: 'likes'},
			{data: 'dtCadastro', render: 
				function(dtCadastro) {
					return moment(dtCadastro).format('LLL');
				}
			},
			{data: 'categoria.titulo'}
		],
		dom: 'Bfrtip',
		buttons: [
			{
				text: 'Editar',
				attr: {
					id: 'btn-editar',
					type: 'button'
				},
				enabled: false
			},
			{
				text: 'Excluir',
				attr: {
					id: 'btn-excluir',
					type: 'button'
				},
				enabled: false
			}
		]
	});
	
	//ação para marcar/desmarcar botões ao clicar na ordenação
	$("#table-server thead").on('click', 'tr', function() {
		table.buttons().disable();
	});
	
	//ação para marcar/desmarcar linhas clicadas
	$("#table-server tbody").on('click', 'tr', function() {
		if($(this).hasClass('selected')) {
			$(this).removeClass('selected');
			table.buttons().disable();
		} else {
			$('tr.selected').removeClass('selected');
			$(this).addClass('selected');
			table.buttons().enable();
		}
	});
	
	//ação do botão de edição
	$("#btn-editar").on('click', function() {
		if(isSelectedRow()) {
			
			var id = getPromoId();
			$.ajax({
				method: "GET",
				url: "/promocao/edit/" + id,
				beforeSend: function() {
					$("#modal-form").modal('show');
				},
				success: function(data) {
					$("#edt_id").val(data.id);
					$("#edt_site").text(data.site);
					$("#edt_titulo").val(data.titulo);
					$("#edt_descricao").val(data.descricao);
					$("#edt_preco").val(data.preco.toLocaleString('pt-br', {
						minimumFractionDigits: 2,
						maximumFractionDigits: 2
					}));
					$("#edt_categoria").val(data.categoria.id);
					$("#edt_linkImagem").val(data.linkImagem);
					$("#edt_imagem").attr('src', data.linkImagem);
				},
				error: function() {
					alert("Ops... Ocorreu um erro, tente novamente.");
				}
			});
			
			
		}
	});
	
	//ação do botão de exclusão(abrir modal)
	$("#btn-excluir").on('click', function() {
		if(isSelectedRow()) {
			$("#modal-delete").modal('show');
		}
	});
	
	//exclusão de uma promoção
	$("#btn-del-modal").on('click', function() {
		var id = getPromoId();
		$.ajax({
			method: "GET",
			url: "/promocao/delete/" + id,
			success: function() {
				$("#modal-delete").modal('hide');
				table.ajax.reload();
			},
			error: function() {
				alert("Ops... Ocorreu um erro, tente mais tarde.");
			}
		});
	});
	
	function getPromoId() {
		return table.row(table.$('tr.selected')).data().id;
	}
	
	function isSelectedRow() {
		var trow = table.row(table.$('tr.selected'));
		
		return trow.data() !== undefined;
	}
});