import { Moment } from 'moment';
import { IEvent } from 'app/shared/model//event.model';
import { ITag } from 'app/shared/model//tag.model';

export interface IPhoto {
    id?: number;
    title?: string;
    imageContentType?: string;
    image?: any;
    height?: number;
    width?: number;
    taken?: Moment;
    uploaded?: Moment;
    event?: IEvent;
    tags?: ITag[];
}

export class Photo implements IPhoto {
    constructor(
        public id?: number,
        public title?: string,
        public imageContentType?: string,
        public image?: any,
        public height?: number,
        public width?: number,
        public taken?: Moment,
        public uploaded?: Moment,
        public event?: IEvent,
        public tags?: ITag[]
    ) {}
}
