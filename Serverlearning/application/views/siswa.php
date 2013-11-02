<?php $this->load->view('layout/header'); ?>
<?php 
if(isset($create) || isset($update)){
    $this->load->view('layout/create_siswa_view');
} else 
$this->load->view('layout/siswa_view');
?>
<?php $this->load->view('layout/footer'); ?>