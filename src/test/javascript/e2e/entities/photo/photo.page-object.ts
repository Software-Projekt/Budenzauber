import { element, by, ElementFinder } from 'protractor';

export class PhotoComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-photo div table .btn-danger'));
    title = element.all(by.css('jhi-photo div h2#page-heading span')).first();

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

export class PhotoUpdatePage {
    pageTitle = element(by.id('jhi-photo-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    titleInput = element(by.id('field_title'));
    imageInput = element(by.id('file_image'));
    heightInput = element(by.id('field_height'));
    widthInput = element(by.id('field_width'));
    takenInput = element(by.id('field_taken'));
    uploadedInput = element(by.id('field_uploaded'));
    eventSelect = element(by.id('field_event'));
    tagSelect = element(by.id('field_tag'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setTitleInput(title) {
        await this.titleInput.sendKeys(title);
    }

    async getTitleInput() {
        return this.titleInput.getAttribute('value');
    }

    async setImageInput(image) {
        await this.imageInput.sendKeys(image);
    }

    async getImageInput() {
        return this.imageInput.getAttribute('value');
    }

    async setHeightInput(height) {
        await this.heightInput.sendKeys(height);
    }

    async getHeightInput() {
        return this.heightInput.getAttribute('value');
    }

    async setWidthInput(width) {
        await this.widthInput.sendKeys(width);
    }

    async getWidthInput() {
        return this.widthInput.getAttribute('value');
    }

    async setTakenInput(taken) {
        await this.takenInput.sendKeys(taken);
    }

    async getTakenInput() {
        return this.takenInput.getAttribute('value');
    }

    async setUploadedInput(uploaded) {
        await this.uploadedInput.sendKeys(uploaded);
    }

    async getUploadedInput() {
        return this.uploadedInput.getAttribute('value');
    }

    async eventSelectLastOption() {
        await this.eventSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async eventSelectOption(option) {
        await this.eventSelect.sendKeys(option);
    }

    getEventSelect(): ElementFinder {
        return this.eventSelect;
    }

    async getEventSelectedOption() {
        return this.eventSelect.element(by.css('option:checked')).getText();
    }

    async tagSelectLastOption() {
        await this.tagSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async tagSelectOption(option) {
        await this.tagSelect.sendKeys(option);
    }

    getTagSelect(): ElementFinder {
        return this.tagSelect;
    }

    async getTagSelectedOption() {
        return this.tagSelect.element(by.css('option:checked')).getText();
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

export class PhotoDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-photo-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-photo'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
