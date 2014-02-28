<link type="text/css" href="css/ui.jqgrid.css" rel="stylesheet" />
<script type="text/javascript" src="js/grid.locale-en.js"></script>
<script type="text/javascript" src="js/jquery.jqGrid.min.js"></script>
<script type="text/javascript">
    jQuery(document).ready(function () {
        loadTable();
    });
    
    var lastsel;
    var defaultEditUrl = "<?=base_url().'rest/submitEditTugas'?>";
    var usedEditUrl = '';
    function loadTable(){
        jQuery("#sg1").jqGrid({
   	url:'<?=base_url().'rest/getDataSiswa'?>?q=1',
	datatype: "xml",
//	height: 190,
        height: 'auto',
        width:685,
   	colNames:['ID Siswa','Nama', 'Jenis Kelamin', 'Alamat'],
   	colModel:[
   		{name:'id_siswa',index:'id_siswa', width:55},
   		{name:'nama',index:'nama', width:90},
   		{name:'jenis_kelamin',index:'jenis_kelamin', width:100},
   		{name:'alamat',index:'alamat', width:80, align:"right"}
//   		{name:'tax',index:'tax', width:80, align:"right"},
//   		{name:'total',index:'total', width:80,align:"right"},
//   		{name:'note',index:'note', width:150, sortable:false}
   	],
   	rowNum:8,
   	rowList:[8,10,20,30],
   	pager: '#psg1',
   	sortname: 'id_siswa',
        viewrecords: true,
        sortorder: "desc",
	multiselect: false,
	subGrid: false,
	caption: "Data Siswa",
	// define the icons in subgrid
	subGridOptions: {
		"plusicon"  : "ui-icon-triangle-1-e",
		"minusicon" : "ui-icon-triangle-1-s",
		"openicon"  : "ui-icon-arrowreturn-1-e"
	},
        onSelectRow: function(ids) {
            if(ids == null) {
//                ids=0;
//                if(jQuery("#sg1_d").jqGrid('getGridParam','records') >0 )
//                {
//                    jQuery("#sg1_d").jqGrid('setGridParam',{url:'<?=base_url().'rest/getDataTugas'?>?q=1&id='+ids,page:1});
//                    jQuery("#sg1_d").jqGrid('setCaption',"Data tugas: "+ids)
//                    .trigger('reloadGrid');
//                }
            } else {
                jQuery("#sg1_d").jqGrid('setGridParam',{
                    url:'<?=base_url().'rest/getDataTugas'?>?q=1&id='+ids,page:1,
                    editurl:defaultEditUrl+'?id_siswa='+ids
                });
                usedEditUrl = defaultEditUrl+'?id_siswa='+ids
                jQuery("#sg1_d").jqGrid('setCaption',"Data tugas: "+ids)
                .trigger('reloadGrid');
            }
        },
	subGridRowExpanded: function(subgrid_id, row_id) {
		var subgrid_table_id, pager_id;
		subgrid_table_id = subgrid_id+"_t";
		pager_id = "p_"+subgrid_table_id;
		$("#"+subgrid_id).html("<table id='"+subgrid_table_id+"' class='scroll'></table><div id='"+pager_id+"' class='scroll'></div>");
		jQuery("#"+subgrid_table_id).jqGrid({
			url:"<?=base_url().'rest/getDataTugas'?>?q=2&id="+row_id,
			datatype: "xml",
			colNames: ['ID Tugas','Judul Tugas','Nilai'],
			colModel: [
				{name:"id_tugas",index:"id_tugas",width:80,key:true},
				{name:"judul_tugas",index:"judul_tugas",width:130},
                                {name:"nilai",index:"nilai",width:130,editable:true}
//				{name:"qty",index:"qty",width:70,align:"right"},
//				{name:"unit",index:"unit",width:70,align:"right"},
//				{name:"total",index:"total",width:70,align:"right",sortable:false}
			],
		   	rowNum:20,
//		   	pager: pager_id,
		   	sortname: 'id_tugas',
		    sortorder: "asc",
		    height: '100%',
                    onSelectRow: function(id){
                        if(id && id!==lastsel){
                                jQuery("#"+subgrid_table_id).jqGrid('restoreRow',lastsel);
                                jQuery("#"+subgrid_table_id).jqGrid('editRow',id,true);
                                lastsel=id;
                        }
                    },
                    editurl: "<?=base_url().'rest/submitEditTugas'?>",
		});
		jQuery("#"+subgrid_table_id).jqGrid('navGrid',"#"+pager_id,{edit:false,add:false,del:false})
	}
});
jQuery("#sg1").jqGrid('navGrid','#psg1',{add:false,edit:false,del:false});

    jQuery("#sg1_d").jqGrid({
	height: 'auto',
   	url:'<?=base_url().'rest/getDataTugas'?>?q=1&id=0',
	datatype: "xml",
   	colNames:['ID Tugas','Judul Tugas','Nilai'],
   	colModel:[
   		{name:'id_tugas',index:'id_tugas', width:55},
   		{name:'judul_tugas',index:'judul_tugas', width:180},
   		{name:'nilai',index:'nilai', width:80, align:"right",editable:true}
//   		{name:'unit',index:'unit', width:80, align:"right"},		
//   		{name:'linetotal',index:'linetotal', width:80,align:"right", sortable:false, search:false}
   	],
   	rowNum:5,
   	rowList:[5,10,20],
   	pager: '#psg1_d',
   	sortname: 'item',
        viewrecords: true,
        sortorder: "asc",
	multiselect: false,
	caption:"Data tugas",
        onSelectRow: function(id){
            if(id && id!==lastsel){
                jQuery("#sg1_d").jqGrid('restoreRow',lastsel);
                jQuery("#sg1_d").jqGrid('editRow',id,true);
                lastsel=id;
                
                var selRowId = jQuery("#sg1_d").jqGrid ('getGridParam', 'selrow')
                var id_tugas = jQuery("#sg1_d").jqGrid ('getCell', selRowId, 'id_tugas'); //get selected id_tugas
                jQuery("#sg1_d").jqGrid('setGridParam',{
                    editurl:usedEditUrl+'&id_tugas='+id_tugas
                });
            }
        },
        editurl: "<?=base_url().'rest/submitEditTugas'?>"
    }).navGrid('#psg1_d',{add:false,edit:false,del:false});
    
//    jQuery("#ms1").click( function() {
//	var s;
//	s = jQuery("#sg1").jqGrid('getGridParam','selarrrow');
//	alert(s);
//    });
    }
</script>
<div id="main_content">
    <span class="emb_left"></span>
    <span class="emb_right"></span>
    <span class="emb_botleft"></span>
    <span class="emb_botright"></span>
    <span class="emb_footrpt"></span>
    <h2>Input Nilai Tugas</h2>
    <div style="padding-left: 10px; padding-right: 10px; overflow-x:auto;">
        <?php //echo $table;?>
        <table id="sg1"></table>
        <div id="psg1"></div>
        <br/>
        <table id="sg1_d"></table>
        <div id="psg1_d"></div>
        <!--<a href="javascript:void(0)" id="ms1">Get Selected id's</a>-->
<!--        <hr />
        <div style="float: left;">
            <form style="padding: 0; margin: 0;" action="home/createbab" method="post">
                <button type="submit" class="btn btn-primary">
                    <i class="icon icon-plus"></i> Tambah Bab
                </button>
            </form>
        </div>-->
    </div>
</div>