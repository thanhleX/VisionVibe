import { Directive, ElementRef, HostListener } from '@angular/core';

@Directive({
  selector: '[appDivDragger]',
  standalone: false,
})
export class DivDraggerDirective {
  private isDragging = false;
  private startX = 0;
  private scrollLeft = 0;
  private velocity = 0;
  private lastMouseX = 0;
  private momentumFrame: any;

  constructor(private el: ElementRef) { }

  @HostListener('mousedown', ['$event'])
  onMouseDown(event: MouseEvent) {
    event.preventDefault();
    this.isDragging = true;
    this.startX = event.pageX - this.el.nativeElement.offsetLeft;
    this.scrollLeft = this.el.nativeElement.scrollLeft;
    this.lastMouseX = event.pageX;
    cancelAnimationFrame(this.momentumFrame);
  }

  @HostListener('mousemove', ['$event'])
  onMouseMove(event: MouseEvent) {
    if (!this.isDragging) return;
    event.preventDefault();
    const x = event.pageX - this.el.nativeElement.offsetLeft;
    const walk = (x - this.startX) * 2;
    this.el.nativeElement.scrollLeft = this.scrollLeft - walk;
    this.velocity = event.pageX - this.lastMouseX;
    this.lastMouseX = event.pageX;
  }

  @HostListener('mouseup')
  onMouseUp() {
    this.isDragging = false;
    this.applyInertia();
  }

  @HostListener('mouseleave')
  onMouseLeave() {
    this.isDragging = false;
  }

  applyInertia() {
    if (Math.abs(this.velocity) < 0.1) return;

    this.velocity *= 0.95;
    this.el.nativeElement.scrollLeft -= this.velocity;
    this.momentumFrame = requestAnimationFrame(() => this.applyInertia());
  }
}
