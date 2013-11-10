<?php $this->load->view('layout/header'); ?>
<?php 
if(isset($create) || isset($update)){
    $this->load->view('layout/create_tugas_view');
} else
$this->load->view('layout/tugas_view');
?>
<?php $this->load->view('layout/footer'); ?>