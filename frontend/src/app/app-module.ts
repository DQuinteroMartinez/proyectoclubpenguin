import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { Navbarcomponent } from './components/navbarcomponent/navbarcomponent';
import { Footercomponent } from './components/footercomponent/footercomponent';
import { HTTP_INTERCEPTORS, provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { AutenticacioninterceptorInterceptor } from './interceptors/autenticacioninterceptor-interceptor';
import { Notfoundcomponent } from './components/notfoundcomponent/notfoundcomponent';

@NgModule({
  declarations: [App, Navbarcomponent, Footercomponent, Notfoundcomponent],
  imports: [BrowserModule, AppRoutingModule],
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideHttpClient(withInterceptorsFromDi()),
    { provide: HTTP_INTERCEPTORS, useClass: AutenticacioninterceptorInterceptor, multi: true },
  ],
  bootstrap: [App],
})
export class AppModule {}
