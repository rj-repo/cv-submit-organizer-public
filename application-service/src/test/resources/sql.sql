insert
into
    applications.documents
(id,content, document_name, type_file)
values
    (1,'\x012345','document', 'PDF');


insert
into
    applications.applications
(id,company_name, status_application,job_offer_name,document_id,user_id)
values
    (1,'company','APPLIED', 'developer',1,1);