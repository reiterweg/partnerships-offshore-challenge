import {Component, OnInit} from '@angular/core';
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {AlertService} from "ngx-alerts";

import {EventService} from "./event.service";
import {Event} from "./event.model";

@Component({
    selector: 'app-event',
    templateUrl: './event.component.html'
})
export class EventComponent implements OnInit {

    events: Event[];

    constructor(
        protected eventService: EventService,
        private alertService: AlertService
    ) {
    }

    ngOnInit() {
        this.loadAll();
    }

    loadAll() {
        this.eventService.query().subscribe(
            (response: HttpResponse<Event[]>) => {
                this.events = response.body;
            },
            (response: HttpErrorResponse) => this.alertService.danger(response.message)
        );
    }

}
