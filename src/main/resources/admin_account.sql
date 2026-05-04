truncate table tbluseraccount;
INSERT INTO tblUserAccount (username, email, password_hash, role, status)
VALUES ('lexuannhan', 'user1@example.com', '$2b$12$tdkAB5xBPS15CW7Jvc.HHew8nNS0mWnl0AxfdQ8r7gyVFXM8ZspBO', 'user', 'active'),
('vuminhphuoc', 'user2@example.com', '$2b$12$JNlf56yTFzhbc4G.EoctEeqzs.FXpcBmk.7jYYOLdhd8txDMr4m32', 'user', 'active'),
('nguyenminhhuyen', 'user3@example.com', '$2b$12$K2MZGRRSaf0zzG6TwA0.peIknew0z68WLGUIJpIxvNRi7vjugZxOy', 'user', 'active');


INSERT INTO tblUserAccount (username, email, password_hash, role, status)
VALUES 
('B23DCKH083', 'admin2@example.com', '$2b$12$FSfytjbtyOPd2zctXBDg2OnVt1KTTP/Wgmkh7vPaiRLJrsqM0/D3S', 'admin', 'active'),
('B23DCKH091', 'admin3@example.com', '$2b$12$9ITYBY9noxs4mevOwAk8YuIPXfx29Kh80r.ltuixfPqtlsOxKe2le', 'admin', 'active');
