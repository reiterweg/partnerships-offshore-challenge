import {Moment} from 'moment';

export class Event {

    constructor(
        public id?: number,
        public name?: string,
        public date?: Moment,
        public venueId?: number,
        public venueName?: string,
        public venueCity?: string,
        public venueState?: string
    ) {
    }

}
