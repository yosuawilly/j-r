<?php $this->load->view('layout/header'); ?>
<?php 
if(isset($create) || isset($update)){
    $this->load->view('layout/create_quiz_view');
} else
$this->load->view('layout/quiz_view');
?>
<?php $this->load->view('layout/footer'); ?>