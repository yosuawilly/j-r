<div id="main_content">
    <span class="emb_left"></span>
    <span class="emb_right"></span>
    <span class="emb_botleft"></span>
    <span class="emb_botright"></span>
    <span class="emb_footrpt"></span>
    <h2><?php echo (isset($create))?'Create New Tugas':'Update Tugas'; ?></h2>
    <?php echo form_open('home/submittugas', array('class'=>'formfield')); ?>
    <input type="hidden" name="id" value="<?php echo $id; ?>"/>
    <input type="hidden" name="proses" value="<?php echo (isset($create))?'create':'update';?>"/>
    <fieldset>
        <legend>Detail Tugas</legend>
        <table>
            <tr class="odd">
                <td class="first">
                    <label>Judul Tugas</label>
                </td>
                <td>:</td>
                <td class="last">
                    <input type="text" name="judul_tugas" value="<?=$judul_tugas?>"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label>Catatan</label>
                </td>
                <td>:</td>
                <td>
                    <input type="text" name="catatan" style="width: 400px;" value="<?=$catatan?>"/>
                </td>
            </tr>
            <tr class="odd">
                <td style="vertical-align: top;">
                    <label>Soal</label>
                </td>
                <td style="vertical-align: top;">:</td>
                <td>
                    <table id="table_soal" class="my_table">
                        <tr>
                            <td>1.</td>
                            <td><textarea name="isi_soal[]" rows="1" cols="3" ><?=$isi_soal[0]?></textarea><br/></td>
                            <td style="vertical-align: top;"><button type="button" class="btn btn-primary" onclick="return addTextAreaSoal();"><i class="icon icon-plus"></i></button></td>
                        </tr>
                        <?php $idx=0; foreach ($isi_soal as $soal) :
                            if($idx==0){
                                $idx++; continue;
                            }?>
                        <tr id="tr_soal<?=($idx+1)?>">
                            <td class="no_soal"><?=($idx+1).'.'?></td>
                            <td><textarea name="isi_soal[]" rows="1" cols="3" ><?=$soal?></textarea><br/></td>
                            <td style="vertical-align: top;"><button type="button" id="btn_del<?=($idx+1)?>" class="btn btn-danger" onclick="return deleteTextAreaSoal(this.id)"><i class="icon icon-trash"></i></button></td>
                        </tr>
                        <?php $idx++; endforeach; ?>
<!--                        <tr>
                            <td>2.</td>
                            <td><textarea name="isi_soal" rows="1" cols="3" ></textarea><br/></td>
                            <td style="vertical-align: top;"><button class="btn btn-danger"><i class="icon icon-trash"></i></button></td>
                        </tr>-->
                    </table>
                </td>
            </tr>
        </table><br/>
        <?php if($error!=null) {?>
        <div id="pesanerror" style="width: 100%;"><?php echo ''.$error; ?></div><br/>
        <?php } ?>
        <button type="submit" class="btn btn-primary">
            <i class="icon icon-plus"></i> Simpan
        </button>
        <button type="submit" name="batal" value="batal" class="btn btn-danger">
            <i class="icon icon-stop"></i> Batal
        </button>
    </fieldset>
    <?php echo form_close(); ?>
</div>
<script type="text/javascript">
    $jum_soal = <?=($idx)?>;
    $id_tr = <?=($idx)?>;

    function addTextAreaSoal(){
        $num = $jum_soal+1;
        $tr_id = $id_tr+1;
        $textToAdd = "<tr id=\"tr_soal"+($tr_id)+"\"><td class=\"no_soal\">"+($num)+".</td>"+
                         "<td><textarea name=\"isi_soal[]\" rows=\"1\" cols=\"3\" ></textarea><br/></td>"+
                         "<td style=\"vertical-align: top;\"><button type=\"button\" id=\"btn_del"+$tr_id+"\" class=\"btn btn-danger\" onclick=\"return deleteTextAreaSoal(this.id);\">"+
                      "<i class=\"icon icon-trash\"></i></button></td></tr>";
//        $oldHtml = $('#table_soal').html();
//        $('#table_soal').html($oldHtml+$textToAdd);
        $('#table_soal').append($textToAdd);
        $jum_soal++;$id_tr++;
        return false;
    }

    function deleteTextAreaSoal(btn_id){
        $tr_id = btn_id.substr(7, btn_id.length);
        $('#tr_soal'+$tr_id).remove();
        $no_soal = 2;
        $('.no_soal').each(function(i, td){
            $(td).html(i+2+'.');
        });
        $jum_soal--;
        return false;
    }
</script>