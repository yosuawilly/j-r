<?php  if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Home extends CI_Controller{
    
    public $user_data = array();

    public $tmpl = array ('table_open'          => '<table class="table table-hover table-bordered" border="0" cellpadding="0" cellspacing="0" style="width:100%">',
                       'heading_row_start'   => '<tr class="heading">',
                       'heading_row_end'     => '</tr>',
                       'heading_cell_start'  => '<th class="center">',
                       'heading_cell_end'    => '</th>',
                       'row_start'           => '<tr>',
                       'row_end'             => '</tr>',
                       'cell_start'          => '<td>',
                       'cell_end'            => '</td>',
                       'row_alt_start'       => '<tr class="even">',
                       'row_alt_end'         => '</tr>',
                       'cell_alt_start'      => '<td>',
                       'cell_alt_end'        => '</td>',
                       'table_close'         => '</table>'
                       );
    
    public function __construct() {
        parent::__construct();
        $this->load->library('session');
        $this->load->library('form_validation');
        $this->load->library('table');
        $this->load->library('pagination');
//        $this->load->library('mpdf');
        $this->load->library('html2pdf');
        $this->load->helper('url');
        
        $this->load->model('bab_model');
        $this->load->model('materi_model');
        $this->load->model('siswa_model');
        $this->load->model('tugas_model');
        $this->load->model('upload_tugas_model');
        $this->load->model('quiz_model');
        
        date_default_timezone_set('Asia/Jakarta');
        $this->form_validation->set_error_delimiters($this->config->item('error_start_delimiter', 'ion_auth'), $this->config->item('error_end_delimiter', 'ion_auth'));
            
        $this->user_data['username'] = $this->session->userdata('username');
    }
    
    public function index(){
        if(!$this->my_auth->logged_in()) redirect ('auth/login', 'refresh');
        
        $this->user_data['title'] = "Home E-Learning Server";
        $this->user_data['home'] = true;
        
        $this->load->view('home', $this->user_data);
    }
    
    public function bab() {
        if(!$this->my_auth->logged_in()) redirect ('auth/login', 'refresh');
        
        $this->user_data['title'] = "Data Bab - E-Learning Server";
        $this->user_data['bab'] = true;
        
        $result = $this->bab_model->get();
        
        $tmpl = array ('table_open'          => '<table class="table table-hover table-bordered" border="0" cellpadding="0" cellspacing="0" style="width:100%">',
                       'heading_row_start'   => '<tr class="heading">',
                       'heading_row_end'     => '</tr>',
                       'heading_cell_start'  => '<th class="center">',
                       'heading_cell_end'    => '</th>',
                       'row_start'           => '<tr>',
                       'row_end'             => '</tr>',
                       'cell_start'          => '<td>',
                       'cell_end'            => '</td>',
                       'row_alt_start'       => '<tr class="even">',
                       'row_alt_end'         => '</tr>',
                       'cell_alt_start'      => '<td>',
                       'cell_alt_end'        => '</td>',
                       'table_close'         => '</table>'
                       );
        
        $this->table->set_template($tmpl);
        $this->table->set_heading('ID Bab', 'Label Bab', 'Judul Bab', 'Action');
        
        foreach($result['rows'] as $row){
            $button_update_delete = '<div style="float:right;"><a style="margin-right:5px;" href="'.base_url().'home/updatebab/'.$row['id_bab'].'" class="btn btn-warning">
            <i class="icon icon-pencil"></i> Update</a>';
            $button_update_delete .= '<a href="'.base_url().'home/deletebab/'.$row['id_bab'].'" class="btn btn-danger" onclick="return deleteData(this,\''.$row['id_bab'].'\');">
            <i class="icon icon-trash"></i> Delete</a></div>';
            $this->table->add_row(
                array('data'=>$row['id_bab'], 'class'=>'center'),
                array('data'=>$row['label_bab'], 'class'=>'center'),
                array('data'=>$row['judul_bab'], 'class'=>'center'),
                $button_update_delete
            );
        }
        $this->user_data['table'] = $this->table->generate();
        
        $this->load->view('bab', $this->user_data);
    }
    
    public function createbab() {
        if(!$this->my_auth->logged_in()) redirect ('auth/login', 'refresh');
        
        $this->user_data['title'] = "Create Bab - E-Learning Server";
        $this->user_data['bab'] = true;
        $this->user_data['id'] = '';
        $this->user_data['create'] = true;
        $this->user_data['labelbab'] = ($this->session->flashdata('labelbab')) ? $this->session->flashdata('labelbab') : '';
        $this->user_data['judulbab'] = ($this->session->flashdata('judulbab')) ? $this->session->flashdata('judulbab') : '';
        $this->user_data['error'] = ($this->session->flashdata('error')) ? $this->session->flashdata('error') : '';
        
        $this->load->view('bab', $this->user_data);
    }
    
    public function updatebab($id_bab=NULL) {
        if(!$this->my_auth->logged_in()) redirect ('auth/login', 'refresh');
        if($id_bab==NULL) redirect ('home/bab', 'refresh');
        
        $row = $this->bab_model->get_by_id($id_bab);
        if(!$row) redirect ('home/bab', 'refresh');
        
        $this->user_data['title'] = "Update Bab - E-Learning Server";
        $this->user_data['bab'] = true;
        $this->user_data['id'] = $id_bab;
        $this->user_data['update'] = true;
        $this->user_data['labelbab'] = ($this->session->flashdata('labelbab')) ? $this->session->flashdata('labelbab') : $row->label_bab;
        $this->user_data['judulbab'] = ($this->session->flashdata('judulbab')) ? $this->session->flashdata('judulbab') : $row->judul_bab;
        $this->user_data['error'] = ($this->session->flashdata('error')) ? $this->session->flashdata('error') : '';
        
        $this->load->view('bab', $this->user_data);
    }
    
    public function deletebab($id_bab=NULL) {
        if(!$this->my_auth->logged_in()) redirect ('auth/login', 'refresh');
        if($id_bab==NULL) redirect ('home/bab', 'refresh');
        
        $result = $this->bab_model->delete($id_bab);
        if($result) redirect ('home/bab', 'refresh');
    }

    public function submitbab() {
        if(!$this->my_auth->logged_in()) redirect ('auth/login', 'refresh');
        if($this->input->post('batal')) redirect ('home/bab', 'refresh');

//        $this->form_validation->set_rules('labelbab', 'Label Bab', 'required|max_length[10]');
//        $this->form_validation->set_rules('judulbab', 'Judul Bab', 'required|max_length[30]');
        
        $id = $this->input->post('id');
        $proses = $this->input->post('proses');
        $labelbab = $this->input->post('labelbab');
        $judulbab = $this->input->post('judulbab');
        
        switch ($proses) {
            case 'create':
                //if ($this->form_validation->run() == false) {
                if(trim($labelbab) == '' || trim($judulbab) == ''){
                    $this->session->set_flashdata('labelbab', $labelbab);
                    $this->session->set_flashdata('judulbab', $judulbab);
                    $this->session->set_flashdata('error', 'All field required');
                    redirect('home/createbab', 'refresh');
                } else {
                    $data = array('label_bab' => $labelbab,
                                  'judul_bab' => $judulbab);
                    $result = $this->bab_model->add($data);
                    if(!$result['error']) redirect ('home/bab', 'refresh');
                    else {
                        $this->session->set_flashdata('labelbab', $labelbab);
                        $this->session->set_flashdata('judulbab', $judulbab);
                        $this->session->set_flashdata('error', $result['error']);
                        redirect('home/createbab', 'refresh');
                    }
                }
                break;
            case 'update':
                if(trim($labelbab) == '' || trim($judulbab) == ''){
                    $this->session->set_flashdata('labelbab', $labelbab);
                    $this->session->set_flashdata('judulbab', $judulbab);
                    $this->session->set_flashdata('error', 'All field required');
                    redirect('home/updatebab/'.$id, 'refresh');
                } else {
                    $data = array('label_bab' => $labelbab,
                                  'judul_bab' => $judulbab);
                    $sukses = $this->bab_model->update($id, $data);
                    if($sukses) redirect ('home/bab', 'refresh');
                }
                break;
            default:
                break;
        }
    }

    public function materi() {
        if(!$this->my_auth->logged_in()) redirect ('auth/login', 'refresh');
        
        $this->user_data['title'] = "Data Materi - E-Learning Server";
        $this->user_data['materi'] = true;

        $result = $this->materi_model->get();

        $this->table->set_template($this->tmpl);
        $this->table->set_heading('ID Materi', 'Judul', 'Isi Materi', 'Bab', 'Semester', 'Action');
        
        foreach($result['rows'] as $row){
            $button_update_delete = '<div style="float:right;"><a style="margin-right:5px;" href="'.base_url().'home/updatemateri/'.$row['id_materi'].'" class="btn btn-warning">
            <i class="icon icon-pencil"></i> Update</a>';
            $button_update_delete .= '<a href="'.base_url().'home/deletemateri/'.$row['id_materi'].'" class="btn btn-danger" onclick="return deleteData(this,\''.$row['id_materi'].'\');">
            <i class="icon icon-trash"></i> Delete</a></div>';
            $isi_materi = $row['isi_materi'];
            $isi_materi = (strlen($isi_materi)>1500) ? substr($isi_materi, 0, 1500) : $isi_materi;
            $isi_materi = '<div style="width: 200px; height: auto; overflow: auto">' . $isi_materi . '</div>';
            
            $attributes = array(
                'class'     =>  'btn btn-warning',
                'width'     =>  '800',
                'height'    =>  '500',
                'screenx'   =>  '\'+((parseInt(screen.width) - 800)/2)+\'',
                'screeny'   =>  '\'+((parseInt(screen.height) - 500)/3)+\''
                );
            $popUp = anchor_popup('home/showMateri/'.$row['id_materi'], 'Show Materi', $attributes);
            
            $this->table->add_row(
                array('data'=>$row['id_materi'], 'class'=>'center'),
                array('data'=>$row['judul'], 'class'=>'center'),
                $popUp,
//                array('data'=>$isi_materi, 'style'=>'width:200px;max-width:200px;'),
//                array('data'=>$row['isi_materi'], 'class'=>'center'),
                array('data'=>$row['label_bab'], 'class'=>'center'),
                array('data'=>$row['semester'], 'class'=>'center'),
                array('data'=>$button_update_delete, 'style'=>'width:164px;')
            );
        }
        $this->user_data['table'] = $this->table->generate();
        
        $this->load->view('materi', $this->user_data);
    }
    
    public function showMateri($id_materi=NULL) {
        if($id_materi == NULL) {
            show_error ('Materi not found'); exit();
        }
        
        $row = $this->materi_model->get_by_id($id_materi);
        if(!$row) {
            show_error ('Materi not found'); exit();
        }
        
        $this->user_data['title'] = $row->judul;
        $this->user_data['isi_materi'] = $row->isi_materi;
        $this->load->view('showMateri', $this->user_data);
    }
    
    public function createmateri() {
        if(!$this->my_auth->logged_in()) redirect ('auth/login', 'refresh');

        $data_bab = $this->bab_model->get();
        $data_bab = $data_bab['rows'];

        $data = ($this->session->flashdata('data')) ? $this->session->flashdata('data') : array();
        
        $this->user_data['title'] = "Create Materi - E-Learning Server";
        $this->user_data['materi'] = true;
        $this->user_data['id'] = '';
        $this->user_data['create'] = true;
        $this->user_data['data_bab'] = $data_bab;
        $this->user_data['judul'] = (isset($data['judul'])) ? $data['judul'] : '';
        $this->user_data['isi_materi'] = (isset($data['isi_materi'])) ? $data['isi_materi'] : '';
        $this->user_data['id_bab'] = (isset($data['id_bab'])) ? $data['id_bab'] : '';
        $this->user_data['semester'] = (isset($data['semester'])) ? $data['semester'] : '';
        
        $this->user_data['error'] = (isset($data['error'])) ? $data['error'] : '';
        
        $this->load->view('materi', $this->user_data);
    }

    public function updatemateri($id_materi=NULL) {
        if(!$this->my_auth->logged_in()) redirect ('auth/login', 'refresh');
        if($id_materi==NULL) redirect ('home/materi', 'refresh');

        $row = $this->materi_model->get_by_id($id_materi);
        if(!$row) redirect ('home/materi', 'refresh');

        $data_bab = $this->bab_model->get();
        $data_bab = $data_bab['rows'];

        $data = ($this->session->flashdata('data')) ? $this->session->flashdata('data') : array();

        $this->user_data['title'] = "Update Materi - E-Learning Server";
        $this->user_data['materi'] = true;
        $this->user_data['id'] = $id_materi;
        $this->user_data['update'] = true;
        $this->user_data['data_bab'] = $data_bab;
        $this->user_data['judul'] = (isset($data['judul'])) ? $data['judul'] : $row->judul;
        $this->user_data['isi_materi'] = (isset($data['isi_materi'])) ? $data['isi_materi'] : $row->isi_materi;
        $this->user_data['id_bab'] = (isset($data['id_bab'])) ? $data['id_bab'] : $row->id_bab;
        $this->user_data['semester'] = (isset($data['semester'])) ? $data['semester'] : $row->semester;
        $this->user_data['error'] = (isset($data['error'])) ? $data['error'] : '';

        $this->load->view('materi', $this->user_data);
    }

    public function deletemateri($id_materi=NULL) {
        if(!$this->my_auth->logged_in()) redirect ('auth/login', 'refresh');
        if($id_materi==NULL) redirect ('home/materi', 'refresh');

        $result = $this->materi_model->delete($id_materi);
        if($result['result']) {
            unlink('materi/'.$result['judul'].'.pdf');
            redirect ('home/materi', 'refresh');
        }
    }

    public function submitmateri() {
        if(!$this->my_auth->logged_in()) redirect ('auth/login', 'refresh');
        if($this->input->post('batal')) redirect ('home/materi', 'refresh');

        $id = $this->input->post('id');
        $proses = $this->input->post('proses');
        $judul = $this->input->post('judul');
        $isi_materi = $this->input->post('isi_materi');
        $id_bab = $this->input->post('id_bab');
        $semester = $this->input->post('semester');

        $data = array('judul'=>$judul, 'isi_materi'=>$isi_materi, 'id_bab'=>$id_bab,
                      'semester'=>$semester);

        switch ($proses) {
            case 'create':
                if(trim($judul)=='' || trim($isi_materi)=='' || trim($id_bab)==''
                        || trim($semester)==''){
                    $data['error'] = 'All field required';
                    $this->session->set_flashdata('data', $data);
                    redirect('home/createmateri', 'refresh');
                } else {
                    $result = $this->materi_model->add($data);
                    if(!$result['error']) {
                        $this->html2pdf->folder('materi/');
                        $this->html2pdf->filename($judul.'.pdf');
                        $this->html2pdf->paper('a4', 'portrait');
                        $this->html2pdf->html($isi_materi);
                        if($this->html2pdf->create('save')) {
                            redirect ('home/materi', 'refresh');
                        } else 
//                        $this->mpdf->WriteHTML($isi_materi);
//                        $this->mpdf->Output('materi/'.$judul.'.pdf','F');
                        redirect ('home/materi', 'refresh');
                    }
                    else {
                        $data['error'] = $result['error'];
                        $this->session->set_flashdata('data', $data);
                        redirect('home/createmateri', 'refresh');
                    }
                }
                break;
            case 'update':
                if(trim($judul)=='' || trim($isi_materi)=='' || trim($id_bab)==''
                        || trim($semester)==''){
                    $data['error'] = 'All field required';
                    $this->session->set_flashdata('data', $data);
                    redirect('home/updatemateri/'.$id, 'refresh');
                } else {
                    $sukses = $this->materi_model->update($id, $data);
                    if($sukses) {
                        $this->html2pdf->folder('materi/');
                        $this->html2pdf->filename($judul.'.pdf');
                        $this->html2pdf->paper('a4', 'portrait');
                        $this->html2pdf->html($isi_materi);
                        if($this->html2pdf->create('save')) {
                            redirect ('home/materi', 'refresh');
                        } else 
//                        $this->mpdf->WriteHTML($isi_materi);
//                        $this->mpdf->Output('materi/'.$judul.'.pdf','F');
                        redirect ('home/materi', 'refresh');
                    }
                }
                break;
            default:
                break;
        }
    }

    public function siswa() {
        if(!$this->my_auth->logged_in()) redirect ('auth/login', 'refresh');
        
        $this->user_data['title'] = "Data Siswa - E-Learning Server";
        $this->user_data['siswa'] = true;

        $result = $this->siswa_model->get();

        $this->table->set_template($this->tmpl);
        $this->table->set_heading('ID Siswa', 'Username', 'Password', 'Nama', 'Jenis Kelamin', 'Alamat', 'Action');

        foreach($result['rows'] as $row){
            $button_update_delete = '<div style="float:right;"><a style="margin-right:5px;" href="'.base_url().'home/updatesiswa/'.$row['id_siswa'].'" class="btn btn-warning">
            <i class="icon icon-pencil"></i> Update</a>';
            $button_update_delete .= '<a href="'.base_url().'home/deletesiswa/'.$row['id_siswa'].'" class="btn btn-danger" onclick="return deleteData(this,\''.$row['id_siswa'].'\');">
            <i class="icon icon-trash"></i> Delete</a></div>';
            $this->table->add_row(
                array('data'=>$row['id_siswa'], 'class'=>'center'),
                array('data'=>$row['username'], 'class'=>'center'),
                array('data'=>$row['password'], 'class'=>'center'),
                array('data'=>$row['nama'], 'class'=>'center'),
                array('data'=>$row['jenis_kelamin'], 'class'=>'center'),
                array('data'=>$row['alamat'], 'class'=>'center'),
                $button_update_delete
            );
        }
        $this->user_data['table'] = $this->table->generate();
        
        $this->load->view('siswa', $this->user_data);
    }
    
    public function createsiswa() {
        if(!$this->my_auth->logged_in()) redirect ('auth/login', 'refresh');

        $data = ($this->session->flashdata('data')) ? $this->session->flashdata('data') : array();
        
        $this->user_data['title'] = "Create Siswa - E-Learning Server";
        $this->user_data['siswa'] = true;
        $this->user_data['id'] = '';
        $this->user_data['create'] = true;
        $this->user_data['id_siswa'] = (isset($data['id_siswa'])) ? $data['id_siswa'] : '';
        $this->user_data['username'] = (isset($data['username'])) ? $data['username'] : '';
        $this->user_data['password'] = (isset($data['password'])) ? $data['password'] : '';
        $this->user_data['nama'] = (isset($data['nama'])) ? $data['nama'] : '';
        $this->user_data['jenis_kelamin'] = (isset($data['jenis_kelamin'])) ? $data['jenis_kelamin'] : 'L';
        $this->user_data['alamat'] = (isset($data['alamat'])) ? $data['alamat'] : '';
        $this->user_data['error'] = (isset($data['error'])) ? $data['error'] : '';
        
        $this->load->view('siswa', $this->user_data);
    }

    public function updatesiswa($id_siswa=NULL) {
        if(!$this->my_auth->logged_in()) redirect ('auth/login', 'refresh');
        if($id_siswa==NULL) redirect ('home/siswa', 'refresh');

        $row = $this->siswa_model->get_by_id($id_siswa);
        if(!$row) redirect ('home/siswa', 'refresh');

        $data = ($this->session->flashdata('data')) ? $this->session->flashdata('data') : array();

        $this->user_data['title'] = "Update Siswa - E-Learning Server";
        $this->user_data['siswa'] = true;
        $this->user_data['id'] = $id_siswa;
        $this->user_data['update'] = true;
        $this->user_data['id_siswa'] = (isset($data['id_siswa'])) ? $data['id_siswa'] : $row->id_siswa;
        $this->user_data['username'] = (isset($data['username'])) ? $data['username'] : $row->username;
        $this->user_data['password'] = (isset($data['password'])) ? $data['password'] : $row->password;
        $this->user_data['nama'] = (isset($data['nama'])) ? $data['nama'] : $row->nama;
        $this->user_data['jenis_kelamin'] = (isset($data['jenis_kelamin'])) ? $data['jenis_kelamin'] : $row->jenis_kelamin;
        $this->user_data['alamat'] = (isset($data['alamat'])) ? $data['alamat'] : $row->alamat;
        $this->user_data['error'] = (isset($data['error'])) ? $data['error'] : '';

        $this->load->view('siswa', $this->user_data);
    }

    public function deletesiswa($id_siswa=NULL) {
        if(!$this->my_auth->logged_in()) redirect ('auth/login', 'refresh');
        if($id_siswa==NULL) redirect ('home/siswa', 'refresh');

        $result = $this->siswa_model->delete($id_siswa);
        if($result) redirect ('home/siswa', 'refresh');
    }

    public function submitsiswa() {
        if(!$this->my_auth->logged_in()) redirect ('auth/login', 'refresh');
        if($this->input->post('batal')) redirect ('home/siswa', 'refresh');

        $id = $this->input->post('id');
        $proses = $this->input->post('proses');
        $id_siswa = $this->input->post('id_siswa');
        $username = $this->input->post('username');
        $password = $this->input->post('password');
        $nama = $this->input->post('nama');
        $jenis_kelamin = $this->input->post('jenis_kelamin');
        $alamat = $this->input->post('alamat');

        $data = array('id_siswa'=>$id_siswa, 'username'=>$username, 'password'=>$password,
                      'nama'=>$nama, 'jenis_kelamin'=>$jenis_kelamin, 'alamat'=>$alamat);

        switch ($proses) {
            case 'create':
                if(trim($id_siswa)=='' || trim($username)=='' || trim($password)==''
                        || trim($nama)=='' || trim($jenis_kelamin)=='' || trim($alamat)==''){
                    $data['error'] = 'All field required';
                    $this->session->set_flashdata('data', $data);
                    redirect('home/createsiswa', 'refresh');
                } else {
                    $result = $this->siswa_model->add($data);
                    if(!$result['error']) redirect ('home/siswa', 'refresh');
                    else {
                        $data['error'] = $result['error'];
                        $this->session->set_flashdata('data', $data);
                        redirect('home/createsiswa', 'refresh');
                    }
                }
                break;
            case 'update':
                if(trim($id_siswa)=='' || trim($username)=='' || trim($password)==''
                        || trim($nama)=='' || trim($jenis_kelamin)=='' || trim($alamat)==''){
                    $data['error'] = 'All field required';
                    $this->session->set_flashdata('data', $data);
                    redirect('home/updatesiswa/'.$id, 'refresh');
                } else {
                    $sukses = $this->siswa_model->update($id, $data);
                    if($sukses) redirect ('home/siswa', 'refresh');
                }
                break;
            default:
                break;
        }
    }

    public function tugas(){
        if(!$this->my_auth->logged_in()) redirect ('auth/login', 'refresh');
        
        $this->user_data['title'] = "Data Tugas - E-Learning Server";
        $this->user_data['tugas'] = true;

        $result = $this->tugas_model->get();

        $this->table->set_template($this->tmpl);
        $this->table->set_heading('ID Tugas', 'Judul Tugas', 'Catatan', 'Soal', 'Action');

        foreach($result['rows'] as $row){
            $button_update_delete = '<div style="float:right;"><a style="margin-right:5px;" href="'.base_url().'home/updatetugas/'.$row['id_tugas'].'" class="btn btn-warning">
            <i class="icon icon-pencil"></i> Update</a>';
            $button_update_delete .= '<a href="'.base_url().'home/deletetugas/'.$row['id_tugas'].'" class="btn btn-danger" onclick="return deleteData(this,\''.$row['id_tugas'].'\');">
            <i class="icon icon-trash"></i> Delete</a></div>';
            $this->table->add_row(
                array('data'=>$row['id_tugas'], 'class'=>'center'),
                array('data'=>$row['judul_tugas'], 'class'=>'center'),
                array('data'=>$row['catatan'], 'class'=>'center'),
                '<button id="show_soal'.$row['id_tugas'].'" class="btn btn-warning" onclick="return viewSoal('.$row['id_tugas'].')">
                 <i class="icon icon-pencil"></i> Show</button>',
                $button_update_delete
            );
        }
        $this->user_data['table'] = $this->table->generate();
        
        $this->load->view('tugas', $this->user_data);
    }

    public function createtugas() {
        if(!$this->my_auth->logged_in()) redirect ('auth/login', 'refresh');

        $data = ($this->session->flashdata('data')) ? $this->session->flashdata('data') : array();

        $this->user_data['title'] = "Create Tugas - E-Learning Server";
        $this->user_data['tugas'] = true;
        $this->user_data['id'] = '';
        $this->user_data['create'] = true;
        $this->user_data['judul_tugas'] = (isset($data['judul_tugas'])) ? $data['judul_tugas'] : '';
        $this->user_data['catatan'] = (isset($data['catatan'])) ? $data['catatan'] : '';
        $this->user_data['isi_soal'] = (isset($data['isi_soal'])) ? $data['isi_soal'] : array('');

        $this->user_data['error'] = (isset($data['error'])) ? $data['error'] : '';

        $this->load->view('tugas', $this->user_data);
    }

    public function updatetugas($id_tugas=NULL) {
        if(!$this->my_auth->logged_in()) redirect ('auth/login', 'refresh');
        if($id_tugas==NULL) redirect ('home/tugas', 'refresh');

        $row = $this->tugas_model->get_by_id($id_tugas);
        $row_soal = $this->tugas_model->get_soal($id_tugas);
        if(!$row || !$row_soal['num_rows']) redirect ('home/tugas', 'refresh');

        $data = ($this->session->flashdata('data')) ? $this->session->flashdata('data') : array();
        $isi_soal = array();

        $this->user_data['title'] = "Update Tugas - E-Learning Server";
        $this->user_data['tugas'] = true;
        $this->user_data['id'] = $id_tugas;
        $this->user_data['update'] = true;
        $this->user_data['judul_tugas'] = (isset($data['judul_tugas'])) ? $data['judul_tugas'] : $row->judul_tugas;
        $this->user_data['catatan'] = (isset($data['catatan'])) ? $data['catatan'] : $row->catatan;
        foreach ($row_soal['rows'] as $soal) {
            $isi_soal[] = $soal['isi_soal'];
        }
        $this->user_data['isi_soal'] = (isset($data['isi_soal'])) ? $data['isi_soal'] : $isi_soal;
        
        $this->user_data['error'] = (isset($data['error'])) ? $data['error'] : '';

        $this->load->view('tugas', $this->user_data);
    }

    public function deletetugas($id_tugas=NULL) {
        if(!$this->my_auth->logged_in()) redirect ('auth/login', 'refresh');
        if($id_tugas==NULL) redirect ('home/tugas', 'refresh');

        $result = $this->tugas_model->delete($id_tugas);
        if($result) redirect ('home/tugas', 'refresh');
    }

    public function submittugas() {
        if(!$this->my_auth->logged_in()) redirect ('auth/login', 'refresh');
        if($this->input->post('batal')) redirect ('home/tugas', 'refresh');

        $id = $this->input->post('id');
        $proses = $this->input->post('proses');
        $judul_tugas = $this->input->post('judul_tugas');
        $catatan = $this->input->post('catatan');
        $isi_soal = $this->input->post('isi_soal');

        $data = array('judul_tugas'=>$judul_tugas, 'catatan'=>$catatan, 'isi_soal'=>$isi_soal);

        switch ($proses) {
            case 'create':
                if(trim($judul_tugas)=='' || trim($catatan)=='' || in_array('', $isi_soal)){
                    $data['error'] = 'All field required';
                    $this->session->set_flashdata('data', $data);
                    redirect('home/createtugas', 'refresh');
                } else {
                    $result = $this->tugas_model->add(array('judul_tugas'=>$judul_tugas, 'catatan'=>$catatan), $isi_soal);
                    if($result['result']) redirect ('home/tugas', 'refresh');
                    else {
                        $data['error'] = $result['error'];
                        $this->session->set_flashdata('data', $data);
                        redirect('home/createtugas', 'refresh');
                    }
                }
                break;
            case 'update':
                if(trim($judul_tugas)=='' || trim($catatan)=='' || in_array('', $isi_soal)){
                    $data['error'] = 'All field required';
                    $this->session->set_flashdata('data', $data);
                    redirect('home/updatetugas/'.$id, 'refresh');
                } else {
                    $sukses = $this->tugas_model->update($id, array('judul_tugas'=>$judul_tugas, 'catatan'=>$catatan), $isi_soal);
                    if($sukses) redirect ('home/tugas', 'refresh');
                }
                break;
            default:
                break;
        }
    }
    
    public function dataUploadTugas($id_tugas=NULL) {
        if(!$this->my_auth->logged_in()) redirect ('auth/login', 'refresh');
        
        $this->user_data['title'] = "Data Upload Tugas - E-Learning Server";
        $this->user_data['upload_tugas'] = true;
        
        $result_tugas = $this->tugas_model->get();
        $result = ($id_tugas!=NULL) ? $this->upload_tugas_model->get_upload_tugas_by_id_tugas($id_tugas) : 
            $this->upload_tugas_model->get_all_upload_tugas();

        $this->table->set_template($this->tmpl);
        $this->table->set_heading('ID', 'Nama Siswa', 'Judul Tugas', 'Nama File', 'Tanggal Upload');
        
        foreach($result['rows'] as $row){
            $link_file = '<a href="'.base_url().'upload_tugas/'.$row['nama_file'].'" onclick="return true;">'.$row['nama_file'].'</a>';
            $this->table->add_row(
                array('data'=>$row['id_upload'], 'class'=>'center'),
                array('data'=>$row['nama'], 'class'=>'center'),
                array('data'=>$row['judul_tugas'], 'class'=>'center'),
                $link_file,
//                array('data'=>$row['nama_file'], 'class'=>'center'),
                array('data'=>$row['tgl_upload'], 'class'=>'center')
            );
        }
        $this->user_data['table'] = $this->table->generate();
        $this->user_data['data_tugas'] = $result_tugas['rows'];
        $this->user_data['id_tugas'] = $id_tugas;
        
        $this->load->view('data_upload_tugas', $this->user_data);
    }
    
    public function quiz(){
        if(!$this->my_auth->logged_in()) redirect ('auth/login', 'refresh');
        
        $this->user_data['title'] = "Data Quiz - E-Learning Server";
        $this->user_data['quiz'] = true;

        $result = $this->quiz_model->get();

        $this->table->set_template($this->tmpl);
        $this->table->set_heading('ID Quiz', 'Jawaban', 'Soal', 'Action');

        foreach($result['rows'] as $row){
            $button_update_delete = '<div style="float:right;"><a style="margin-right:5px;" href="'.base_url().'home/updatequiz/'.$row['id_quiz'].'" class="btn btn-warning">
            <i class="icon icon-pencil"></i> Update</a>';
            $button_update_delete .= '<a href="'.base_url().'home/deletequiz/'.$row['id_quiz'].'" class="btn btn-danger" onclick="return deleteData(this,\''.$row['id_quiz'].'\');">
            <i class="icon icon-trash"></i> Delete</a></div>';
            
            $btn_show = '<button id="show_soal'.$row['id_quiz'].'" class="btn btn-warning" onclick="return viewSoal('.$row['id_quiz'].')">
                 <i class="icon icon-pencil"></i> Show</button>';
            
            $this->table->add_row(
                array('data'=>$row['id_quiz'], 'class'=>'center', 'style'=>'width:40px;'),
                array('data'=>$btn_show, 'style'=>'width:72px;'),
                array('data'=>$row['soal_quiz'], 'class'=>'center'),
                array('data'=>$button_update_delete, 'style'=>'width:164px;')
            );
        }
        $this->user_data['table'] = $this->table->generate();
        
        $this->load->view('quiz', $this->user_data);
    }

}