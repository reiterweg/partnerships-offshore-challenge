import {NgModule} from '@angular/core';
import {HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {AlertModule} from "ngx-alerts";

import {AppComponent} from './app.component';
import {AppRoutingModule} from './app-routing.module';
import {HeaderComponent} from './common/header/header.component';
import {VenueComponent} from './entities/venue/venue.component';
import {VenueSaveComponent} from './entities/venue/venue-save.component';
import {EventComponent} from './entities/event/event.component';
import {EventSaveComponent} from './entities/event/event-save.component';

@NgModule({
    declarations: [
        AppComponent,
        HeaderComponent,
        VenueComponent,
        VenueSaveComponent,
        EventComponent,
        EventSaveComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        FormsModule,
        HttpClientModule,
        BrowserAnimationsModule,
        AlertModule.forRoot({maxMessages: 5, timeout: 5000, position: 'left'})
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule {
}
