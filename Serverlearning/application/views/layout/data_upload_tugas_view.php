<div id="main_content">
    <span class="emb_left"></span>
    <span class="emb_right"></span>
    <span class="emb_botleft"></span>
    <span class="emb_botright"></span>
    <span class="emb_footrpt"></span>
    <h2>Data Upload Tugas</h2>
    
    <div style="padding-left: 10px; padding-right: 10px; overflow-x:auto;">
        <select name="pilihan_tugas" id="pilihan_tugas" >
            <option value="all" >Semua</option>
            <?php foreach($data_tugas as $tugas) : ?>
            <option value="<?=$tugas['id_tugas']?>" <?=($id_tugas==$tugas['id_tugas'] ? 'selected':'')?> >
                    <?=$tugas['judul_tugas']?>
            </option>
            <?php endforeach;?>
        </select>
        
        <?php echo $table;?>
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
<script type="text/javascript">
    var BASE_URL = '<?=base_url()?>';
    
    $('#pilihan_tugas').change(function(obj){
        var id_tugas = $(this).val();
        if(id_tugas!='all')
            window.location.href = BASE_URL+'home/dataUploadTugas/'+id_tugas;
        else window.location.href = BASE_URL+'home/dataUploadTugas/'
    });
</script>