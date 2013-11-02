<?php $this->load->view('layout/header'); ?>
<?php 
if(isset($create) || isset($update)){
    $this->load->view('layout/create_bab_view');
} else 
$this->load->view('layout/bab_view');
?>
<?php $this->load->view('layout/footer'); ?>