import { IVeranstaltung } from 'app/shared/model//veranstaltung.model';

export interface IBild {
    id?: number;
    invisible?: boolean;
    title?: string;
    thumbUrl?: string;
    url?: string;
    veranstaltung?: IVeranstaltung;
}

export class Bild implements IBild {
    constructor(
        public id?: number,
        public invisible?: boolean,
        public title?: string,
        public thumbUrl?: string,
        public url?: string,
        public veranstaltung?: IVeranstaltung
    ) {
        this.invisible = this.invisible || false;
    }
}
