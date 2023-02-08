import React from 'react';

export default function Footer() {
  return (
    <footer className="section-footer border-top bg-dark">
      <div className="container">
        <section className="footer-top padding-y py-5">
          <div className="row pt-3">
            <aside className="col-md-4 footer-about">
              <article className="d-flex pb-3">
                <div><img alt="#" src="img/logo_web.png" className="logo-footer mr-3" /></div>
                <div>
                  <h6 className="title text-white">About Us</h6>
                  <p className="text-muted">Some short text about company like You might remember the Dell computer commercials in which a youth reports.</p>
                  <div className="d-flex align-items-center">
                    <a className="btn btn-icon btn-outline-light mr-1 btn-sm" title="Github" target="https://github.com/toshko89" href="https://github.com/toshko89"><i className="feather-github"></i></a>
                    <a className="btn btn-icon btn-outline-light mr-1 btn-sm" title="Instagram" target="_blank" href="/#"><i className="feather-instagram"></i></a>
                    <a className="btn btn-icon btn-outline-light mr-1 btn-sm" title="Youtube" target="_blank" href="/#"><i className="feather-youtube"></i></a>
                    <a className="btn btn-icon btn-outline-light mr-1 btn-sm" title="Twitter" target="_blank" href="/#"><i className="feather-twitter"></i></a>
                  </div>
                </div>
              </article>
            </aside>
          </div>
        </section>
      </div>
      <section className="footer-copyright border-top py-3 bg-light">
        <div className="container d-flex align-items-center">
          <p className="mb-0">2023 Food-Corner This site is made with educational purpose only! No rights reserved</p>
        </div>
      </section>
    </footer>

  );
}