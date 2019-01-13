import { element, by, ElementFinder } from 'protractor';

export class EventComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-event div table .btn-danger'));
    title = element.all(by.css('jhi-event div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class EventUpdatePage {
    pageTitle = element(by.id('jhi-event-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    archivierungsDatumInput = element(by.id('field_archivierungsDatum'));
    erstellungsDatumInput = element(by.id('field_erstellungsDatum'));
    freigegebenInput = element(by.id('field_freigegeben'));
    grusswortInput = element(by.id('field_grusswort'));
    nameInput = element(by.id('field_name'));
    userSelect = element(by.id('field_user'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setArchivierungsDatumInput(archivierungsDatum) {
        await this.archivierungsDatumInput.sendKeys(archivierungsDatum);
    }

    async getArchivierungsDatumInput() {
        return this.archivierungsDatumInput.getAttribute('value');
    }

    async setErstellungsDatumInput(erstellungsDatum) {
        await this.erstellungsDatumInput.sendKeys(erstellungsDatum);
    }

    async getErstellungsDatumInput() {
        return this.erstellungsDatumInput.getAttribute('value');
    }

    getFreigegebenInput() {
        return this.freigegebenInput;
    }
    async setGrusswortInput(grusswort) {
        await this.grusswortInput.sendKeys(grusswort);
    }

    async getGrusswortInput() {
        return this.grusswortInput.getAttribute('value');
    }

    async setNameInput(name) {
        await this.nameInput.sendKeys(name);
    }

    async getNameInput() {
        return this.nameInput.getAttribute('value');
    }

    async userSelectLastOption() {
        await this.userSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async userSelectOption(option) {
        await this.userSelect.sendKeys(option);
    }

    getUserSelect(): ElementFinder {
        return this.userSelect;
    }

    async getUserSelectedOption() {
        return this.userSelect.element(by.css('option:checked')).getText();
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class EventDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-event-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-event'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
