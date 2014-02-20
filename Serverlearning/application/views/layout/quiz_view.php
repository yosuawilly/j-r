<div id="main_content">
    <span class="emb_left"></span>
    <span class="emb_right"></span>
    <span class="emb_botleft"></span>
    <span class="emb_botright"></span>
    <span class="emb_footrpt"></span>
    <h2>Data Quiz</h2>
    <div style="padding-left: 10px; padding-right: 10px; overflow-x:auto;">
        <?php echo $table;?>
        <hr />
        <div style="float: left;">
            <form style="padding: 0; margin: 0;" action="home/createquiz" method="post">
                <button type="submit" class="btn btn-primary">
                    <i class="icon icon-plus"></i> Tambah Quiz
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

    function viewSoal(id_tugas){
        $.ajax({
            url: 'rest/get_jawaban/'+id_tugas,
            data: {
//                json:jQuery.toJSON(data)
            },
            cache: false,
            type: 'GET',
            statusCode: {
                404: function() {
                    alert("page not found");
                }
            },
            success: function(data, status, xhr){
//                alert(data)
                var result = $.parseJSON(data);
                if(result==null){
                    alert("Problem dengan server");
                }else {
                    view(result);
                }
            },
            error: function(xhr, status, errorMsg){
                alert(errorMsg);
            }
        });
        
        return false;
    }

    function view(result){
        $('#t_judul').html(result.judul);
        $('#t_soal').empty();
        $idx = 97; //97 is charCode from a (next b,c,d dst...)
        $jawabans = result.jawaban;
        for($i=0; $i<$jawabans.length; $i++){
            var $num = String.fromCharCode($idx)
            $rowJawaban = "<tr><td>"+$num+".&nbsp</td><td>"+$jawabans[$i].jawaban
            if($jawabans[$i].benar=='t') $rowJawaban += "<span>&nbsp(benar)</span>";
            $rowJawaban += "</td></tr>";
            $('#t_soal').append($rowJawaban);
            $idx++;
        }
        $("#soal_view").dialog({
            title: "View Jawaban",
            resizable: false,
            position: 'center',
            modal: true,
            width: 360,
            height: "auto",
            hide: 'explode',
            show: 'clip',
            buttons:[
                {
                    text:"Tutup",
                    click:function(){
                        $(this).dialog("close");
                    }
                }//,
//                {
//                    text:"Batal",
//                    click:function(){
//                        $(this).dialog("close");
//                    }
//                }
            ]
        });
    }
</script>
<div id="soal_view" style="display: none;" >
    <center><h4 id="t_judul">Judul Tugas</h4></center>
    <hr style="margin: 5px 0;"/>
    <table id="t_soal">
        <tr>
            <td>1.</td>
            <td>Apa yang dimaksud kimia?</td>
        </tr>
        <tr>
            <td>2.</td>
            <td>Apa yang dimaksud kimia?</td>
        </tr>
        <tr>
            <td>3.</td>
            <td>Apa yang dimaksud kimia?</td>
        </tr>
    </table>
</div>