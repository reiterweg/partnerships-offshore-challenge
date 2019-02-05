import {Component, ElementRef, EventEmitter, OnInit, Output, ViewChild} from '@angular/core';
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {AlertService} from "ngx-alerts";

import {Venue} from "./venue.model";
import {VenueService} from "./venue.service";

@Component({
    selector: 'app-venue-save',
    templateUrl: './venue-save.component.html'
})
export class VenueSaveComponent implements OnInit {

    @ViewChild('btnClose') btnClose: ElementRef;
    @Output() created = new EventEmitter();
    venue: Venue;
    isSaving: boolean;

    constructor(
        protected venueService: VenueService,
        private alertService: AlertService
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.venue = new Venue();
    }

    save() {
        this.isSaving = true;
        this.venueService
            .create(this.venue)
            .subscribe(
                (response: HttpResponse<Venue>) => this.onSaveSuccess(),
                (response: HttpErrorResponse) => this.onSaveError()
            );
    }

    protected onSaveSuccess() {
        this.created.emit();
        this.isSaving = false;
        this.btnClose.nativeElement.click();
        this.alertService.success('Venue was successfully created');
    }

    protected onSaveError() {
        this.isSaving = false;
        this.alertService.danger('There was an error creating Venue');
    }

}
