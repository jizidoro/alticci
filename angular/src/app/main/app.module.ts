import {
  APP_BASE_HREF,
  LocationStrategy,
  PathLocationStrategy,
  PlatformLocation,
} from '@angular/common';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { ErrorHandler, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AlticciRepository } from '../core/repositories/alticci.repository';
import { AlticciWebRepository } from '../data/repository/alticci-web-repository/alticci-web.repository';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FlexLayoutModule } from '@angular/flex-layout';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

export function getBaseHref(platformLocation: PlatformLocation): string {
  return platformLocation.getBaseHrefFromDOM();
}

@NgModule({
  declarations: [AppComponent],
  imports: [FlexLayoutModule, BrowserModule, HttpClientModule, AppRoutingModule, BrowserAnimationsModule],
  providers: [{ provide: AlticciRepository, useClass: AlticciWebRepository }],
  bootstrap: [AppComponent],
})
export class AppModule {}
