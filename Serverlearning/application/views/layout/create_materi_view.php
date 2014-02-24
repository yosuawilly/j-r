<script type="text/javascript">
    jQuery(document).ready(function () {
        bkLib.onDomLoaded(function() {
            new nicEditor({uploadURI:"<?php echo base_url().'rest/upload';?>",iconsPath: "<?php echo base_url().'css/images/nicEditorIcons.gif';?>"}).panelInstance('isi_materi');
        });
        //showHideRowLink();
        $('#btn-add-link').click(function(){
            addLink();
        });
    });
    
    var $countLink = <?=count($link_video)?>;
    
    function showHideRowLink(){
        if($('#table_link').find('tr').length == 0){
            $('#row_link').hide();
        } else $('#row_link').show(200, function(){});
    }
    
    function addLink(){
        $countLink++;
        $textLink = "<tr id=\"tr-link\">"+
                            "<td class=\"no-row\">"+$countLink+".</td>"+
                            "<td><input name=\"link[]\" type=\"text\" /><br/></td>"+
                            "<td style=\"vertical-align: top;\"><button type=\"button\" class=\"btn btn-danger\" onclick=\"return deleteTextLink(this);\">"+
                            "<i class=\"icon icon-trash\"></i></button></td>"+
                        "</tr>";
        $($textLink).appendTo($('#table_link')).hide().show(200, function(){});
//        $('#table_link').append($textLink);
        showHideRowLink();
        return false;
    }
    
    function deleteTextLink(td){
        $(td).parents('#tr-link').hide(200, function(){
            $countLink--;
            $(td).parents('#tr-link').remove();
            $('.no-row').each(function(i, td){
                $(td).html(i+1+'.');
            });
            showHideRowLink();
        });
        
//        $countLink--;
//        $(td).parents('#tr-link').remove();
//        $('.no-row').each(function(i, td){
//            $(td).html(i+1+'.');
//        });
//        showHideRowLink();
        return false;
    }
</script>
<div id="main_content">
    <span class="emb_left"></span>
    <span class="emb_right"></span>
    <span class="emb_botleft"></span>
    <span class="emb_botright"></span>
    <span class="emb_footrpt"></span>
    <h2><?php echo (isset($create))?'Create New Materi':'Update Materi'; ?></h2>
    <?php echo form_open('home/submitmateri', array('class'=>'formfield')); ?>
    <input type="hidden" name="id" value="<?php echo $id; ?>"/>
    <input type="hidden" name="proses" value="<?php echo (isset($create))?'create':'update';?>"/>
    <fieldset>
        <legend>Detail Materi</legend>
        <table>
            <tr class="odd">
                <td class="first">
                    <label>Judul</label>
                </td>
                <td>:</td>
                <td class="last">
                    <input type="text" name="judul" style="width: 400px;" value="<?=$judul?>" <?=(isset($update)?'readonly':'')?>/>
                </td>
            </tr>
            <tr>
                <td style="vertical-align: top;">
                    <label>Isi Materi</label>
                </td>
                <td style="vertical-align: top;">:</td>
                <td>
                    <div style="margin-bottom: 7px;">
                        <textarea name="isi_materi" id="isi_materi" cols="3" rows="3" style="width: 500px;" ><?=$isi_materi?></textarea>
                    </div>
                </td>
            </tr>
            <tr class="odd">
                <td>
                    <label>Bab</label>
                </td>
                <td>:</td>
                <td>
                    <select name="id_bab">
                        <?php foreach ($data_bab as $row) : ?>
                        <option value="<?=$row['id_bab']?>" <?=($id_bab==$row['id_bab'])?'selected':'';?>><?=$row['label_bab'].' - '.$row['judul_bab']?></option>
                        <?php endforeach; ?>
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                    <label>Semester</label>
                </td>
                <td>:</td>
                <td>
                    <select name="semester">
                        <option value="1" <?=($semester=='1')?'selected':'';?>>1 (Satu)</option>
                        <option value="2" <?=($semester=='2')?'selected':'';?>>2 (Dua)</option>
                    </select>
                </td>
            </tr>
            <tr id="row_link" class="odd" style="<?=(count($link_video)>0 ? '':'display: none;')?>">
                <td style="vertical-align: top;">
                    <label>Link Materi Video</label>
                </td>
                <td style="vertical-align: top;">:</td>
                <td>
                    <table id="table_link" class="my_table">
                        <?php $countLink = 0;
                            foreach ($link_video as $link) :
                               $countLink++;
                        ?>
                        <tr id="tr-link">
                            <td class="no-row"><?=$countLink?>.</td>
                            <td><input name="link[]" type="text" value="<?=$link?>" /><br/></td>
                            <td style="vertical-align: top;"><button type="button" class="btn btn-danger" onclick="return deleteTextLink(this);"><i class="icon icon-trash"></i></button></td>
                        </tr>
                        <?php endforeach; ?>
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
        <button id="btn-add-link" type="button" name="addLink" value="tambah_link_video" class="btn btn-primary" >
            <i class="icon icon-plus"></i> Tambah Link Video
        </button>
    </fieldset>
</div>