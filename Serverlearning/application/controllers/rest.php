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
        $this->load->model('upload_tugas_model');
        $this->load->model('quiz_model');
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
            case 't_quiz':
                $quizs = $this->quiz_model->get();
                $version = $this->version_model->get_by_nama_table($table);
                $result = array('version'=>$version->version, 'table'=>$quizs['rows']);
                echo json_encode($result);
                break;
            case 't_jawaban_quiz':
                $jawaban_quiz = $this->quiz_model->get_all_jawaban();
                $version = $this->version_model->get_by_nama_table($table);
                $result = array('version'=>$version->version, 'table'=>$jawaban_quiz['rows']);
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
//            $status['url'] =  NICUPLOAD_URI ."/".$id;
            $status['url'] =  NICUPLOAD_PATH ."/".$id;

            $this->upload->nicupload_output($status, false);
            exit;
        }
    }
    
    public function uploadTugas() {
        $url_tugas = 'upload_tugas';
        
//        echo $_POST['id_siswa'].' '.$_POST['id_tugas'];
        
        if($_SERVER['REQUEST_METHOD']=='POST') {
            $id_siswa = $_POST['id_siswa'];
            $id_tugas = $_POST['id_tugas'];
            $file = $_FILES['uploaded_file'];
            
//            echo end(explode('.', $file['name'])); exit();
            
            if($id_siswa==NULL || $id_tugas==NULL || $file==NULL){
                echo 'Parameter tidak lengkap'; exit();
            }
            
            $extention = end(explode('.', $file['name']));
            $newFileName = $id_siswa.'_'.$id_tugas."_.".$extention;
            $url_tugas = $url_tugas . '/' .$newFileName;
//            $url_tugas = $url_tugas . '/' .basename($file['name']);
            if(file_exists($url_tugas)){
                echo 'Anda sudah mengupload tugas sebelumnya'; exit();
            }
            
            if(move_uploaded_file($file['tmp_name'], $url_tugas)){
                $data = array('id_siswa'=>$id_siswa, 'id_tugas'=>$id_tugas, 'nama_file'=>$newFileName, 'tgl_upload'=>date("Y-m-d H:i:s"));
                
                $result = $this->upload_tugas_model->add($data);
                if(!$result['error']) {
                    echo 'File Upload Completed';
                }
                else {
                    unlink($url_tugas);
                    echo $result['error'];
                }
            } else echo 'File Not Uploaded';
        } else {
            echo "Method Not Allowed";
        }
    }

    public function get_soal($id_tugas) {
        $row = $this->tugas_model->get_by_id($id_tugas);
        $row_soal = $this->tugas_model->get_soal($id_tugas);

        $result = array('judul_tugas'=>$row->judul_tugas, 'soal'=>$row_soal['rows']);
        echo json_encode($result);
    }
    
    public function get_jawaban($id_quiz) {
        $row = $this->quiz_model->get_by_id($id_quiz);
        $row_jawaban = $this->quiz_model->get_jawaban($id_quiz);

//        $result = array('judul_tugas'=>$row->judul_tugas, 'soal'=>$row_soal['rows']);
        $result = array('judul'=>'Jawaban', 'jawaban'=>$row_jawaban['rows']);
        echo json_encode($result);
    }

    public function getTableVersion(){
        $result = $this->version_model->get();
        echo json_encode($result);
    }
    
    public function getLinkVideo($id_materi) {
        $result = $this->materi_model->get_link_video($id_materi, TRUE);
        echo json_encode(array('linkVideos'=>$result));
    }

}