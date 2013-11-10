<?php $this->load->view('layout/header'); ?>
<?php 
if(isset($create) || isset($update)){
    $this->load->view('layout/create_materi_view');
} else 
$this->load->view('layout/materi_view');
?>
<?php $this->load->view('layout/footer'); ?>