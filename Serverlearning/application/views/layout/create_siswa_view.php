<div id="main_content">
    <span class="emb_left"></span>
    <span class="emb_right"></span>
    <span class="emb_botleft"></span>
    <span class="emb_botright"></span>
    <span class="emb_footrpt"></span>
    <h2><?php echo (isset($create))?'Create New Siswa':'Update Siswa'; ?></h2>
    <?php echo form_open('home/submitsiswa', array('class'=>'formfield')); ?>
    <input type="hidden" name="id" value="<?php echo $id; ?>"/>
    <input type="hidden" name="proses" value="<?php echo (isset($create))?'create':'update';?>"/>
    <fieldset>
        <legend>Detail Siswa</legend>
        <table>
            <tr class="odd">
                <td class="first">
                    <label>ID Siswa</label>
                </td>
                <td class="last">:
                    <input type="text" name="id_siswa" value="<?php echo $id_siswa; ?>"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label>Username</label>
                </td>
                <td>:
                    <input type="text" name="username" value="<?php echo $username; ?>"/>
                </td>
            </tr>
            <tr class="odd">
                <td>
                    <label>Password</label>
                </td>
                <td>:
                    <input type="text" name="password" value="<?php echo $password; ?>"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label>Nama</label>
                </td>
                <td>:
                    <input type="text" name="nama" value="<?php echo $nama; ?>"/>
                </td>
            </tr>
            <tr class="odd">
                <td>
                    <label>Jenis Kelamin</label>
                </td>
                <td>:
                    <select name="jenis_kelamin">
                        <option value="L" <?php echo ($jenis_kelamin=='L')?'selected':'';?>>Laki-laki</option>
                        <option value="P" <?php echo ($jenis_kelamin=='P')?'selected':'';?>>Perempuan</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                    <label>Alamat</label>
                </td>
                <td>:
                    <textarea name="alamat" cols="3" rows="3"><?php echo $alamat; ?></textarea>
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