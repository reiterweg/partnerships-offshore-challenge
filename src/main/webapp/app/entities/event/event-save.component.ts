import {Component, ElementRef, EventEmitter, OnInit, Output, ViewChild} from '@angular/core';
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {AlertService} from "ngx-alerts";
import * as moment from 'moment';

import {DATE_TIME_FORMAT} from "../../common/constants";
import {EventService} from "./event.service";
import {VenueService} from "../venue/venue.service";
import {Event} from "../event/event.model";
import {Venue} from "../venue/venue.model";

@Component({
    selector: 'app-event-save',
    templateUrl: './event-save.component.html'
})
export class EventSaveComponent implements OnInit {

    @ViewChild('btnClose') btnClose: ElementRef;
    @Output() created = new EventEmitter();
    event: Event;
    isSaving: boolean;
    venues: Venue[];

    constructor(
        protected eventService: EventService,
        protected venueService: VenueService,
        private alertService: AlertService
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.event = new Event();
        this.venueService.query().subscribe(
            (response: HttpResponse<Venue[]>) => {
                this.venues = response.body;
            },
            (response: HttpErrorResponse) => this.alertService.danger(response.message)
        );
    }

    save() {
        this.isSaving = true;
        this.eventService
            .create(this.event)
            .subscribe(
                (response: HttpResponse<Event>) => this.onSaveSuccess(response),
                (response: HttpErrorResponse) => this.onSaveError(response)
            );
    }

    validDate(field) {
        if (field && field.untouched && (field.value === undefined || field.value === null || field.value === "")) {
            return true;
        }
        return moment(field.value, DATE_TIME_FORMAT, true).isValid();
    }

    protected onSaveSuccess(response) {
        this.isSaving = false;
        this.created.emit();
        this.btnClose.nativeElement.click();
        this.alertService.success('Event was successfully created');
    }

    protected onSaveError(response) {
        this.isSaving = false;
        this.alertService.danger('There was an error creating Event');
    }

}
