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
    date: string;

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
        this.event.date = this.date != null ? moment(this.date, DATE_TIME_FORMAT) : null;
        this.eventService
            .create(this.event)
            .subscribe(
                (response: HttpResponse<Event>) => this.onSaveSuccess(),
                (response: HttpErrorResponse) => this.onSaveError()
            );
    }

    validDate(date) {
        return date != null && moment(date, DATE_TIME_FORMAT);
    }

    protected onSaveSuccess() {
        this.created.emit();
        this.isSaving = false;
        this.btnClose.nativeElement.click();
        this.alertService.success('Event was successfully created');
    }

    protected onSaveError() {
        this.isSaving = false;
        this.alertService.danger('There was an error creating Event');
    }

}
