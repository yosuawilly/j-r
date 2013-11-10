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
</div>
<script type="text/javascript">
    jQuery(document).ready(function () {
        bkLib.onDomLoaded(function() {
            new nicEditor({uploadURI:"<?php echo base_url().'rest/upload';?>",iconsPath: "<?php echo base_url().'css/images/nicEditorIcons.gif';?>"}).panelInstance('isi_materi');
        });
    });
</script>