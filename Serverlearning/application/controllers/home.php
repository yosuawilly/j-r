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
        $this->load->helper('url');
        
        $this->load->model('bab_model');
        $this->load->model('materi_model');
        $this->load->model('siswa_model');
        
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
        
        $id = $this->input->post('id');
        $proses = $this->input->post('proses');
        $labelbab = $this->input->post('labelbab');
        $judulbab = $this->input->post('judulbab');
        
        switch ($proses) {
            case 'create':
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
        $this->table->set_heading('ID Materi', 'Judul', 'Isi Materi', 'Bab', 'Semester');
        
        $this->load->view('materi', $this->user_data);
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

    public function tugas(){
        if(!$this->my_auth->logged_in()) redirect ('auth/login', 'refresh');
        
        $this->user_data['title'] = "Data Tugas - E-Learning Server";
        $this->user_data['tugas'] = true;
        
        $this->load->view('tugas', $this->user_data);
    }

}