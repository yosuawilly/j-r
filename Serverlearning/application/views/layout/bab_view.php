<div id="main_content">
    <span class="emb_left"></span>
    <span class="emb_right"></span>
    <span class="emb_botleft"></span>
    <span class="emb_botright"></span>
    <span class="emb_footrpt"></span>
    <h2>Data Bab</h2>
<!--    <h3><?php //echo 'Hello, Admin'; ?><br />
        <span></span>
    </h3>-->
    <div style="padding-left: 10px; padding-right: 10px; overflow-x:auto;">
        <?php echo $table;?>
        <hr />
        <div style="float: left;">
            <form style="padding: 0; margin: 0;" action="home/createbab" method="post">
                <button type="submit" class="btn btn-primary">
                    <i class="icon icon-plus"></i> Tambah Bab
                </button>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    function deleteData(a,id_bab){
        $("#konfirmasi").dialog({
            title: "Konfirmasi",
            resizable: false,
            position: 'center',
            modal: true,
            width: 360,
            height: 140,
//            hide: 'fold',
            hide: 'explode',
            show: 'clip',
            buttons:[
                {
                    text:"Ok",
                    click:function(){
                        window.location.href = $(a).attr('href');
                    }
                },
                {
                    text:"Batal",
                    click:function(){
                        $(this).dialog("close");
                    }
                }
            ]
        });
        return false;
    }
</script>