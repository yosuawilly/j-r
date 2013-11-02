<div id="main_content">
    <span class="emb_left"></span>
    <span class="emb_right"></span>
    <span class="emb_botleft"></span>
    <span class="emb_botright"></span>
    <span class="emb_footrpt"></span>
    <h2><?php echo (isset($create))?'Create New Bab':'Update Bab'; ?></h2>
<!--    <h3><?php //echo 'Hello, Admin'; ?><br />
        <span></span>
    </h3>-->
    <?php echo form_open('home/submitbab', array('class'=>'formfield')); ?>
    <input type="hidden" name="id" value="<?php echo $id; ?>"/>
    <input type="hidden" name="proses" value="<?php echo (isset($create))?'create':'update';?>"/>
    <fieldset>
        <legend>Detail Bab</legend>
        <table>
            <tr class="odd">
                <td class="first">
                    <label>Label Bab</label>
                </td>
                <td class="last">:
                    <input type="text" name="labelbab" value="<?php echo $labelbab; ?>"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label>Judul Bab</label>
                </td>
                <td>:
                    <input type="text" name="judulbab" value="<?php echo $judulbab; ?>"/>
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