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
    
    public function getDataSiswa(){
        $page = $_GET['page']; // get the requested page
        $limit = $_GET['rows']; // get how many rows we want to have into the grid
        $sidx = $_GET['sidx']; // get index row - i.e. user click to sort
        $sord = $_GET['sord']; // get the direction
        if(!$sidx) $sidx =1;

        // connect to the database
//        $db = mysql_connect($dbhost, $dbuser, $dbpassword)
//        or die("Connection Error: " . mysql_error());
//
//        mysql_select_db($database) or die("Error conecting to db.");
//        $result = mysql_query("SELECT COUNT(*) AS count FROM invheader a, clients b WHERE a.client_id=b.client_id");
//        $row = mysql_fetch_array($result,MYSQL_ASSOC);
        $result = $this->siswa_model->get();
        $count = count($result['rows']);
        if( $count > 0 ) {
                $total_pages = ceil($count/$limit);
        } else {
                $total_pages = 0;
        }
        if ($page > $total_pages) $page=$total_pages;
        $start = $limit*$page - $limit; // do not put $limit*($page - 1)
        if ($start < 0) $start = 0;
//        $SQL = "SELECT a.id, a.invdate, b.name, a.amount,a.tax,a.total,a.note FROM invheader a, clients b WHERE a.client_id=b.client_id ORDER BY $sidx $sord LIMIT $start , $limit";
//        $result = mysql_query( $SQL ) or die("Couldnt execute query.".mysql_error());

        if ( stristr($_SERVER["HTTP_ACCEPT"],"application/xhtml+xml") ) {
        header("Content-type: application/xhtml+xml;charset=utf-8"); } else {
        header("Content-type: text/xml;charset=utf-8");
        }
        $et = ">";
        echo "<?xml version='1.0' encoding='utf-8'?$et\n";

        echo "<rows>";
        echo "<page>".$page."</page>";
        echo "<total>".$total_pages."</total>";
        echo "<records>".$count."</records>";
        // be sure to put text data in CDATA
        foreach ($result['rows'] as $row) {
                echo "<row id='". $row['id_siswa']."'>";			
                echo "<cell>". $row['id_siswa']."</cell>";
                echo "<cell>". $row['nama']."</cell>";
                //echo "<cell><![CDATA[". $row[name]."]]></cell>";
                echo "<cell>". $row['jenis_kelamin']."</cell>";
                echo "<cell>". $row['alamat']."</cell>";
//                echo "<cell>". $row[total]."</cell>";
//                echo "<cell><![CDATA[". $row[note]."]]></cell>";
                echo "</row>";
        }
        echo "</rows>";
    }
    
    public function getDataTugas() {
        $examp = $_GET["q"]; //query number

        $id = $_GET['id'];

        // connect to the database
//        $db = mysql_connect($dbhost, $dbuser, $dbpassword)
//        or die("Connection Error: " . mysql_error());

//        mysql_select_db($database) or die("Error conecting to db.");
//        $SQL = "SELECT num, item, qty, unit FROM invlines WHERE id=".$id." ORDER BY item";
//        $result = mysql_query( $SQL ) or die("Couldnt execute query.".mysql_error());
        
        $result = $this->tugas_model->get_nilai_tugas_by_siswa($id);

        if ( stristr($_SERVER["HTTP_ACCEPT"],"application/xhtml+xml") ) {
        header("Content-type: application/xhtml+xml;charset=utf-8"); } else {
        header("Content-type: text/xml;charset=utf-8");
        }
        $et = ">";
        echo "<?xml version='1.0' encoding='utf-8'?$et\n";
        echo "<rows>";
        // be sure to put text data in CDATA
        foreach ($result['rows'] as $row) {
                echo "<row>";			
                echo "<cell>". $row['id_tugas']."</cell>";
//                echo "<cell><![CDATA[". $row[item]."]]></cell>";
                echo "<cell>". $row['judul_tugas']."</cell>";
                echo "<cell>". ($row['nilai']==''?'0':$row['nilai'])."</cell>";
//                echo "<cell>". number_format($row[qty]*$row[unit],2,'.',' ')."</cell>";
                echo "</row>";
        }
        echo "</rows>";
    }
    
    public function submitEditTugas() {
        $nilai = $this->input->post('nilai');
        $oper = $this->input->post('oper'); // operation
        $id = $this->input->post('id');
        
        $id_siswa = $this->input->get('id_siswa');
        $id_tugas = $this->input->get('id_tugas');
        
        $this->tugas_model->save_nilai($id_siswa, $id_tugas, $nilai);
        
//        $dom = new DOMDocument("1.0", "UTF-8");
//        $soals = $dom->createElement('soals');
//        $posts = '';
//        $gets = '';
//        foreach ($_POST as $key => $post) {
//            $posts .= $key.'='.$post.' ';
//        }
//        foreach ($_GET as $key => $get) {
//            $gets .= $key.'='.$get.' ';
//        }
//        $soals->setAttribute('postData', 'post : '.$posts .' get : '.$gets);
//        $soals->setAttribute('postData', $nilai.$oper.$id.$id_siswa.$id_tugas);
//        $dom->appendChild($soals);
//        $dom->save('materi/post.xml');
    }
    
    public function getNilaiTugas($id_siswa) {
        $result = $this->tugas_model->get_nilai_tugas_by_siswa2($id_siswa);
        
        $nilaiTugas = array('nilaiTugas'=>$result['rows']);
        echo json_encode($nilaiTugas);
    }

}