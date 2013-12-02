<?php

class Rest extends CI_Controller {
    
    public function __construct() {
        parent::__construct();
        $this->load->library('upload');

        $this->load->model('bab_model');
        $this->load->model('materi_model');
        $this->load->model('siswa_model');
        $this->load->model('tugas_model');
        $this->load->model('version_model');
    }
    
    public function login($username, $password) {
        $siswa = $this->siswa_model->get_by_username($username);
        if(!$siswa){
            $result = array('status'=>'0','fullMessage'=>'Username anda salah');
            echo json_encode($result);
        } else {
            if($password != $siswa->password){
                $result = array('status'=>'0','fullMessage'=>'Password anda salah');
                echo json_encode($result);
            } else {
                $result = array('status'=>'1','fullMessage'=>'Login Sukses','siswa'=>$siswa);
                echo json_encode($result);
            }
        }
    }

    public function getDataTable($table) {
        switch ($table) {
            case 't_bab':
                $bab = $this->bab_model->get();
                $version = $this->version_model->get_by_nama_table($table);
                $result = array('version'=>$version->version, 'table'=>$bab['rows']);
                echo json_encode($result);
                break;
            case 't_materi':
                $materi = $this->materi_model->get();
                $version = $this->version_model->get_by_nama_table($table);
                $result = array('version'=>$version->version, 'table'=>$materi['rows']);
                echo json_encode($result);
                break;
            case 't_tugas':
                $tugas = $this->tugas_model->get();
                $version = $this->version_model->get_by_nama_table($table);
                $result = array('version'=>$version->version, 'table'=>$tugas['rows']);
                echo json_encode($result);
                break;
            case 't_soal_tugas':
                $soal_tugas = $this->tugas_model->get_all_soal();
                $version = $this->version_model->get_by_nama_table($table);
                $result = array('version'=>$version->version, 'table'=>$soal_tugas['rows']);
                echo json_encode($result);
                break;
            default:
                $result = array('status'=>'0','fullMessage'=>'Table tidak ditemukan');
                echo json_encode($result);
                break;
        }
    }

    public function upload() {
        define('NICUPLOAD_PATH', 'upload_image'); // Set the path (relative or absolute) to
                                                  // the directory to save image files

        $url_image = base_url().'upload_image';
        define('NICUPLOAD_URI', $url_image);   // Set the URL (relative or absolute) to
                                               // the directory defined above

        $nicupload_allowed_extensions = array('jpg','jpeg','png','gif','bmp');

        if(!function_exists('json_encode')) {
            die('{"error" : "Image upload host does not have the required dependicies (json_encode/decode)"}');
        }

        if($_SERVER['REQUEST_METHOD']=='POST') { // Upload is complete

            $file = $_FILES['image'];
            $image = $file['tmp_name'];
            $id = $file['name'];

            $max_upload_size = $this->upload->ini_max_upload_size();
            if(!$file) {
                $this->upload->nicupload_error('Must be less than '.$this->upload->bytes_to_readable($max_upload_size));
            }

            $ext = strtolower(substr(strrchr($file['name'], '.'), 1));
            @$size = getimagesize($image);
            if(!$size || !in_array($ext, $nicupload_allowed_extensions)) {
                $this->upload->nicupload_error('Invalid image file, must be a valid image less than '.$this->upload->bytes_to_readable($max_upload_size));
            }

            $filename = $id;
            $path = NICUPLOAD_PATH.'/'.$filename;

            if(!move_uploaded_file($image, $path)) {
                $this->upload->nicupload_error('Server error, failed to move file');
            }

            $status = array();
            $status['done'] = 1;
            $status['width'] = $size[0];
            $rp = realpath($path);
            $status['url'] =  NICUPLOAD_URI ."/".$id;

            $this->upload->nicupload_output($status, false);
            exit;
        }
    }

    public function get_soal($id_tugas) {
        $row = $this->tugas_model->get_by_id($id_tugas);
        $row_soal = $this->tugas_model->get_soal($id_tugas);

        $result = array('judul_tugas'=>$row->judul_tugas, 'soal'=>$row_soal['rows']);
        echo json_encode($result);
    }

    public function getTableVersion(){
        $result = $this->version_model->get();
        echo json_encode($result);
    }

}